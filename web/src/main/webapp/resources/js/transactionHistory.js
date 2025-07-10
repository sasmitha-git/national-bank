document.addEventListener('DOMContentLoaded', ()=>{
    getTransactionHistory();
    getAccounts();
    getScheduledTasks();
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



async function getScheduledTasks() {
    try {
        const userId = document.getElementById("userId").value;
        const response = await fetch(`/national-bank/api/scheduled-tasks?userId=${userId}`);
        const tasks = await response.json();

        const table = document.getElementById("scheduledTaskTableBody");
        table.innerHTML = ""; // clear existing rows

        tasks.forEach(task => {
            const row = document.createElement("tr");
            const dateTime = new Date(task.nextExecutionTime).toLocaleString();

            const actionColumn = task.status === "ACTIVE"
                ? `<button onclick="cancelTask(${task.id})">Cancel</button>`
                : `<button disabled style="background-color: #ccc; cursor: not-allowed;">Completed</button>`;

            row.innerHTML = `
                <td>${task.fromAccount}</td>
                <td>${task.toAccount}</td>
                <td>${task.amount}</td>
                <td>${task.status}</td>
                <td>${dateTime}</td>
                <td>${actionColumn}</td>
            `;

            table.appendChild(row);
        });

    } catch (error) {
        console.error("Error loading scheduled tasks:", error);
    }
}

function cancelTask(taskId) {
    fetch(`/national-bank/api/scheduled-tasks/${taskId}`, {
        method: "DELETE"
    }).then(() => location.reload());
}


async function getAccounts(){
    try{
        const userId = document.getElementById("userId")?.value;
        if(!userId) throw new Error("User Id Not Found");

        const response = await fetch(`/national-bank/api/accounts?userId=${userId}`);
        if(!response.ok) throw new Error("Failed to get accounts");

        const accounts = await response.json();
        const grid = document.getElementById("accountGrid");
        grid.innerHTML = accounts.map(account =>`
        
        <div class="account-card">
        <h3>${account.accountType} Account</h3>
        <p>Account Number: ${account.accountNumber}</p>
        <p>Balance: ${account.balance}</p>
        </div>
        
        `).join('');
    }catch (error){

        console.log("Error loading .......", error.message);
    }
}