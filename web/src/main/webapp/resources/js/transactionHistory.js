document.addEventListener('DOMContentLoaded', ()=>{
    getTransactionHistory();
});

async function getTransactionHistory(){
    try{
        const response = await fetch("http://localhost:8080/national-bank/transaction-history");

        const history = await response.json();
        showTransactions(history);

    }catch (error){
        console.error("Error Getting Transaction History",error);
    }
}

function showTransactions(history){
    const tbody = document.getElementById('transactionTableBody');
    tbody.innerHTML = '';

    history.forEach(transaction =>{
        const date = new Date(transaction.timestamp).toLocaleString();
        const tr = document.createElement('tr');

        tr.innerHTML = `
        <td>${date}</td>
        <td>${transaction.transactionType}</td>
        <td>${transaction.amount}</td>
        <td>${transaction.status}</td>
        
        `;
        tbody.appendChild(tr);
    });
}



document.addEventListener("DOMContentLoaded", function () {
    const userId = document.getElementById("userId").value;
    fetch(`/national-bank/api/scheduled-tasks?userId=${userId}`)
        .then(response => response.json())
        .then(tasks => {
            const table = document.getElementById("scheduledTaskTableBody");
            tasks.forEach(task => {
                const row = document.createElement("tr");
                row.innerHTML = `
                        <td>${task.fromAccount.accountNumber}</td>
                        <td>${task.toAccount.accountNumber}</td>
                        <td>${task.amount}</td>
                        <td>${task.status}</td>
                        <td>${task.nextExecutionTime}</td>
                        <td>
                            ${task.status === "ACTIVE"
                    ? `<button onclick="cancelTask(${task.id})">Cancel</button>`
                    : "N/A"}
                        </td>
                    `;
                table.appendChild(row);
            });
        });
});

function cancelTask(taskId) {
    fetch(`/national-bank/api/scheduled-tasks/${taskId}`, {
        method: "DELETE"
    }).then(() => location.reload());
}


