function openAddUserModal() {
    document.getElementById("modalTitle").innerText = "Add New User";
    document.getElementById("userForm").reset();
    document.getElementById("saveUserBtn").style.display = "inline-block";
    document.getElementById("userModal").style.display = "flex";
}

function closeModal() {
    document.getElementById("userModal").style.display = "none";
    document.querySelectorAll('#userForm input, #userForm select').forEach(el => el.disabled = false);
}


function openProfileModal() {
    const modal = document.getElementById('profileModal');
    modal.style.display = 'flex';
}

function closeProfileModal() {
    const modal = document.getElementById('profileModal');
    modal.style.display = 'none';
}

function openVerificationModal() {
    const modal = document.getElementById('verificationModal');
    modal.style.display = 'flex';
    // Focus first input field
    document.querySelector('.code-input').focus();
}

function closeVerificationModal() {
    document.getElementById('verificationModal').style.display = 'none';
}