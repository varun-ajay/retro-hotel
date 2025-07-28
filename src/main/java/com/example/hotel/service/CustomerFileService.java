package com.example.hotel.service;

import com.example.hotel.model.Customer;
import com.example.hotel.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class CustomerFileService {

    private final Path dataFile;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private List<Customer> cache = new ArrayList<>();

    public CustomerFileService(@Value("${app.data-file}") String dataFilePath) {
        this.dataFile = Path.of(dataFilePath);
    }

    @PostConstruct
    public void init() throws IOException {
        if (!Files.exists(dataFile)) {
            Files.createFile(dataFile);
            JsonUtils.write(dataFile, new ArrayList<Customer>());
        }
        readFromDisk();
    }

    public List<Customer> getAll() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(cache);
        } finally {
            lock.readLock().unlock();
        }
    }

    public Customer add(Customer c) throws IOException {
        lock.writeLock().lock();
        try {
            cache.add(c);
            writeToDisk();
            return c;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean deleteById(String id) throws IOException {
        lock.writeLock().lock();
        try {
            boolean removed = cache.removeIf(c -> c.getId().equals(id));
            if (removed) {
                writeToDisk();
            }
            return removed;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void readFromDisk() throws IOException {
        lock.writeLock().lock();
        try {
            cache = JsonUtils.read(dataFile, new TypeReference<List<Customer>>() {});
            if (cache == null) cache = new ArrayList<>();
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void writeToDisk() throws IOException {
        JsonUtils.write(dataFile, cache);
    }
}
