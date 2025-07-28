const API = '/api/customers';
const tbody = document.getElementById('customersBody');
const form = document.getElementById('customerForm');
const errorBox = document.getElementById('error');

async function fetchCustomers() {
  try {
    const res = await fetch(API);
    const data = await res.json();
    tbody.innerHTML = '';
    data.forEach(addRowAnimated);
  } catch (e) {
    showError('Failed to load customers.');
  }
}

function addRowAnimated(c) {
  const tr = buildRow(c);
  tr.classList.add('fade-in', 'slide-in');
  tbody.appendChild(tr);
}

function buildRow(c) {
  const tr = document.createElement('tr');
  tr.dataset.id = c.id;

  const tdName = document.createElement('td');
  tdName.textContent = c.name;

  const tdPhone = document.createElement('td');
  tdPhone.textContent = c.phoneNumber;

  const tdRoom = document.createElement('td');
  tdRoom.textContent = c.checkInRoom;

  const tdAction = document.createElement('td');
  const delBtn = document.createElement('button');
  delBtn.textContent = 'Delete';
  delBtn.className = 'btn btn-danger';
  delBtn.addEventListener('click', () => deleteCustomer(c.id, tr));
  tdAction.appendChild(delBtn);

  tr.appendChild(tdName);
  tr.appendChild(tdPhone);
  tr.appendChild(tdRoom);
  tr.appendChild(tdAction);

  return tr;
}

async function deleteCustomer(id, tr) {
  try {
    tr.classList.add('fade-out', 'slide-out');
    await new Promise(r => setTimeout(r, 250));
    const res = await fetch(`${API}/${encodeURIComponent(id)}`, { method: 'DELETE' });
    if (res.status === 200) {
      tr.remove();
    } else {
      showError('Customer not found.');
      tr.classList.remove('fade-out', 'slide-out');
    }
  } catch (e) {
    showError('Delete failed.');
  }
}

form.addEventListener('submit', async (e) => {
  e.preventDefault();
  clearError();

  const payload = {
    name: form.name.value.trim(),
    phoneNumber: form.phoneNumber.value.trim(),
    checkInRoom: form.checkInRoom.value.trim()
  };

  if (!payload.name || !payload.phoneNumber || !payload.checkInRoom) {
    showError('Please fill in all fields.');
    return;
  }

  try {
    const res = await fetch(API, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (!res.ok) {
      const body = await res.json().catch(() => ({}));
      showError(body?.message || 'Validation error');
      return;
    }

    const created = await res.json();

    form.reset();
    addRowAnimated(created);
  } catch (e) {
    showError('Add failed.');
  }
});

function showError(msg) {
  errorBox.textContent = msg;
  errorBox.classList.remove('hidden');
}

function clearError() {
  errorBox.textContent = '';
  errorBox.classList.add('hidden');
}

fetchCustomers();
