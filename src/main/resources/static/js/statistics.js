document.addEventListener("DOMContentLoaded", async () => {
    const response = await fetch("/api/statistics/getApplication");
    const data = await response.json();

    let approved = 0, rejected = 0, pending = 0;
    const educationCount = {};
    const purposeCount = {};
    const statusCount = {};

    data.forEach(app => {
        const sum = app.amount || 0;
        const status = app.status || "Неизвестно";

        if (status === "Одобрено") approved += sum;
        else if (status === "Отказано") rejected += sum;
        else pending += sum;

        educationCount[app.education] = (educationCount[app.education] || 0) + 1;
        purposeCount[app.purpose] = (purposeCount[app.purpose] || 0) + 1;
        statusCount[status] = (statusCount[status] || 0) + 1;
    });

    document.getElementById("approved-sum").textContent = `Выдано: ${approved.toLocaleString()} ₽`;
    document.getElementById("rejected-sum").textContent = `Отказано: ${rejected.toLocaleString()} ₽`;
    document.getElementById("pending-sum").textContent = `В рассмотрении: ${pending.toLocaleString()} ₽`;

    const makeChart = (ctxId, label, dataMap) => {
        const ctx = document.getElementById(ctxId).getContext('2d');
        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: Object.keys(dataMap),
                datasets: [{
                    label,
                    data: Object.values(dataMap),
                    backgroundColor: [
                        '#3498db', '#e74c3c', '#f1c40f', '#2ecc71', '#9b59b6',
                        '#e67e22', '#1abc9c', '#34495e', '#95a5a6'
                    ]
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { position: 'bottom' },
                    title: {
                        display: true,
                        text: label
                    }
                }
            }
        });
    };

    makeChart("educationChart", "Образование заемщиков", educationCount);
    makeChart("purposeChart", "Цели кредитования", purposeCount);
    makeChart("statusChart", "Статусы заявок", statusCount);
});
