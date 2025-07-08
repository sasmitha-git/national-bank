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
