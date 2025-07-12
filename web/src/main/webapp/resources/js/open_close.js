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
    document.getElementById('profileModal').style.display = 'block';
}

function closeProfileModal() {
    document.getElementById('profileModal').style.display = 'none';
}
