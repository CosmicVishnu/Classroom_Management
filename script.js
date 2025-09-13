document.addEventListener('DOMContentLoaded', () => {

    let users = [
        { id: 1, name: 'Aditya Verma', rollNo: '101', role: 'Student' },
        { id: 2, name: 'Priya Sharma', rollNo: '102', role: 'Student' },
        { id: 3, name: 'Rohan Mehta', rollNo: '103', role: 'Student' },
        { id: 4, name: 'Dr. Anjali Rao', rollNo: 'T-01', role: 'Teacher' }
    ];

    const tableBody = document.getElementById('userTableBody');

    const renderUsers = () => {
        tableBody.innerHTML = ''; 
        users.forEach(user => {
            const row = document.createElement('tr');
            // Classes added to each <td> to match the <th> for color-coding
            row.innerHTML = `
                <td class="col-id">${user.id}</td>
                <td class="col-name">
                    <div class="cell-content"><span>${user.name}</span><button class="inline-btn inline-edit-btn" data-id="${user.id}" data-field="name">✏️</button></div>
                </td>
                <td class="col-rollno">
                    <div class="cell-content"><span>${user.rollNo}</span><button class="inline-btn inline-edit-btn" data-id="${user.id}" data-field="rollNo">✏️</button></div>
                </td>
                <td class="col-role">
                    <div class="cell-content"><span>${user.role}</span><button class="inline-btn inline-edit-btn" data-id="${user.id}" data-field="role">✏️</button></div>
                </td>
                <td class="col-actions">
                    <button class="action-btn delete-row-btn" data-id="${user.id}">🗑️</button>
                </td>
            `;
            tableBody.appendChild(row);
        });
    };

    tableBody.addEventListener('click', (event) => {
        const target = event.target.closest('button');
        if (!target) return;

        const id = parseInt(target.dataset.id);

        if (target.classList.contains('delete-row-btn')) {
            if (confirm('Are you sure you want to delete this entire row?')) {
                users = users.filter(user => user.id !== id);
                renderUsers();
            }
        }

        if (target.classList.contains('inline-edit-btn')) {
            renderUsers();
            const field = target.dataset.field;
            const user = users.find(u => u.id === id);
            const cell = target.closest('td');
            
            let inputHtml;
            if (field === 'role') {
                inputHtml = `<select class="edit-input">
                    <option value="Student" ${user.role === 'Student' ? 'selected' : ''}>Student</option>
                    <option value="Teacher" ${user.role === 'Teacher' ? 'selected' : ''}>Teacher</option>
                    <option value="Admin" ${user.role === 'Admin' ? 'selected' : ''}>Admin</option>
                </select>`;
            } else {
                inputHtml = `<input type="text" value="${user[field]}" class="edit-input">`;
            }
            
            cell.innerHTML = `
                <div class="cell-content">
                    ${inputHtml}
                    <button class="inline-btn inline-save-btn" data-id="${id}" data-field="${field}">✔️</button>
                    <button class="inline-btn inline-cancel-btn">❌</button>
                </div>`;
            cell.querySelector('.edit-input').focus();
        }

        if (target.classList.contains('inline-save-btn')) {
            const field = target.dataset.field;
            const input = target.closest('.cell-content').querySelector('.edit-input');
            const userIndex = users.findIndex(u => u.id === id);
            if (userIndex !== -1) {
                users[userIndex][field] = input.value;
            }
            renderUsers();
        }
        
        if (target.classList.contains('inline-cancel-btn')) {
            renderUsers();
        }
    });

    renderUsers();
});