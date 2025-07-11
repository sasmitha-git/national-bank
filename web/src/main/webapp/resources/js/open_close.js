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
