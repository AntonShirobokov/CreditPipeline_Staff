document.addEventListener("DOMContentLoaded", async () => {
    const response = await fetch("/api/statistics/getApplication");
    const data = await response.json();

    let approved = 0, rejected = 0, pending = 0;
    const educationCount = {};
    const purposeCount = {};
    const statusCount = {};

    const ageGroups = {
        "18-25": 0,
        "26-35": 0,
        "36-45": 0,
        "46-60": 0,
        "60+": 0,
        "Неизвестно": 0
    };

    const incomeByEmployment = {};
    const countByEmployment = {};

    data.forEach(app => {
        const sum = app.amount || 0;
        const status = app.status || "Неизвестно";

        if (status === "Одобрено") approved += sum;
        else if (status === "Отказано") rejected += sum;
        else pending += sum;

        educationCount[app.education] = (educationCount[app.education] || 0) + 1;
        purposeCount[app.purpose] = (purposeCount[app.purpose] || 0) + 1;
        statusCount[status] = (statusCount[status] || 0) + 1;

        const age = app.age;
        if (typeof age === "number") {
            if (age >= 18 && age <= 25) ageGroups["18-25"]++;
            else if (age >= 26 && age <= 35) ageGroups["26-35"]++;
            else if (age >= 36 && age <= 45) ageGroups["36-45"]++;
            else if (age >= 46 && age <= 60) ageGroups["46-60"]++;
            else if (age > 60) ageGroups["60+"]++;
            else ageGroups["Неизвестно"]++;
        } else {
            ageGroups["Неизвестно"]++;
        }

        const employment = app.typeOfEmployment || "Неизвестно";
        const income = app.income || 0;

        incomeByEmployment[employment] = (incomeByEmployment[employment] || 0) + income;
        countByEmployment[employment] = (countByEmployment[employment] || 0) + 1;
    });

    document.getElementById("approved-sum").textContent = `Выдано: ${approved.toLocaleString()} ₽`;
    document.getElementById("rejected-sum").textContent = `Отказано: ${rejected.toLocaleString()} ₽`;
    document.getElementById("pending-sum").textContent = `В рассмотрении: ${pending.toLocaleString()} ₽`;

    const makeChart = (ctxId, label, dataMap, type = "pie") => {
        const ctx = document.getElementById(ctxId).getContext('2d');
        new Chart(ctx, {
            type,
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
                    title: { display: true, text: label }
                }
            }
        });
    };

    const averageIncomeByEmployment = {};
    Object.keys(incomeByEmployment).forEach(key => {
        averageIncomeByEmployment[key] = Math.round(incomeByEmployment[key] / countByEmployment[key]);
    });

    makeChart("educationChart", "Образование заемщиков", educationCount);
    makeChart("purposeChart", "Цели кредитования", purposeCount);
    makeChart("statusChart", "Статусы заявок", statusCount);
    makeChart("ageChart", "Возраст заемщиков", ageGroups, "bar");
    makeChart("incomeEmploymentChart", "Средний доход по типу занятости", averageIncomeByEmployment, "bar");
});
