document.addEventListener("DOMContentLoaded", () => {
    const searchBtn = document.getElementById("searchBtn");
    const resultDiv = document.getElementById("result");

    const searchEmployeeBtn = document.getElementById("searchEmployeeBtn");
    const employeeResultDiv = document.getElementById("employeeResult");

    // --- Поиск заявки ---
    searchBtn.addEventListener("click", async () => {
        const id = document.getElementById("applicationId").value.trim();
        if (!id) {
            alert("Введите ID заявки");
            return;
        }

        resultDiv.style.display = "none";
        resultDiv.innerHTML = "Загрузка...";

        try {
            const response = await fetch(`/api/statistics/getInfoApplication?applicationId=${id}`);
            if (!response.ok) {
                resultDiv.style.display = "block";
                resultDiv.innerHTML = "<p>Заявка не найдена.</p>";
                return;
            }

            const data = await response.json();
            renderApplication(data);
        } catch (error) {
            resultDiv.style.display = "block";
            resultDiv.innerHTML = "<p>Ошибка при загрузке данных.</p>";
            console.error(error);
        }
    });

    function renderApplication(app) {
        resultDiv.style.display = "block";

        let employeeInfo = "";
        if (app.status === "Одобрено" || app.status === "Отказано") {
            employeeInfo = `<p><strong>Сотрудник:</strong> ${app.employee?.username || "Неизвестно"}</p>`;
        }

        const refusalInfo = app.status === "Отказано"
            ? `<p><strong>Причина отказа:</strong> ${app.reasonForRefusal}</p>`
            : "";

        resultDiv.innerHTML = `
            <h3 style="margin-bottom: 20px;">Заявка №${app.id}</h3>
            <p><strong>ФИО:</strong> ${app.user.lastName} ${app.user.firstName} ${app.user.middleName}</p>
            <p><strong>Телефон:</strong> ${app.user.phone}</p>
            <p><strong>Email:</strong> ${app.user.email}</p>
            <p><strong>Сумма кредита:</strong> ${app.amount} руб.</p>
            <p><strong>Цель:</strong> ${app.purpose}</p>
            <p><strong>Статус:</strong> ${app.status}</p>
            ${employeeInfo}
            ${refusalInfo}
            <p><strong>Ставка:</strong> ${app.percentageRate}%</p>
            <p><strong>Дата подачи:</strong> ${new Date(app.dateOfCreation).toLocaleString()}</p>
            <p><strong>Дата рассмотрения:</strong> ${new Date(app.dateOfReview).toLocaleString()}</p>

            <details class="details">
                <summary>Показать паспортные данные</summary>
                <table class="details-table">
                    <tr><th>Серия и номер</th><td>${app.user.passport.series} ${app.user.passport.number}</td></tr>
                    <tr><th>Дата рождения</th><td>${app.user.passport.birthDate}</td></tr>
                    <tr><th>Место рождения</th><td>${app.user.passport.birthPlace}</td></tr>
                    <tr><th>Кем выдан</th><td>${app.user.passport.issuedBy}</td></tr>
                    <tr><th>Дата выдачи</th><td>${app.user.passport.issueDate}</td></tr>
                    <tr><th>Код подразделения</th><td>${app.user.passport.departmentCode}</td></tr>
                    <tr><th>ИНН</th><td>${app.user.passport.inn}</td></tr>
                    <tr><th>СНИЛС</th><td>${app.user.passport.snils}</td></tr>
                    <tr><th>Адрес регистрации</th><td>${app.user.passport.registrationAddress}</td></tr>
                </table>
            </details>
        `;
    }

    // --- Поиск по сотруднику ---
    searchEmployeeBtn.addEventListener("click", async () => {
        const username = document.getElementById("employeeUsername").value.trim();
        if (!username) {
            alert("Введите username сотрудника");
            return;
        }

        employeeResultDiv.style.display = "none";
        employeeResultDiv.innerHTML = "Загрузка...";

        try {
            const response = await fetch(`/api/statistics/getInfoEmployee?username=${encodeURIComponent(username)}`);
            if (!response.ok) {
                employeeResultDiv.style.display = "block";
                employeeResultDiv.innerHTML = "<p style=\"margin-top: 20px;\">Сотрудник не найден.</p>";
                return;
            }

            const data = await response.json();
            renderEmployee(data);
        } catch (error) {
            employeeResultDiv.style.display = "block";
            employeeResultDiv.innerHTML = "<p>Ошибка при загрузке данных.</p>";
            console.error(error);
        }
    });

    function renderEmployee(data) {
        employeeResultDiv.style.display = "block";
        employeeResultDiv.innerHTML = `
        <div class="employee-card">
            <h3>Информация о сотруднике</h3>
            <p><strong>ID:</strong> ${data.id}</p>
            <p><strong>Username:</strong> ${data.username}</p>
            <p><strong>ФИО:</strong> ${data.lastName} ${data.firstName} ${data.middleName}</p>
            <p><strong>Обработанные заявки (ID):</strong></p>
            <ul>${data.listApplication.map(id => `<li>Заявка №${id}</li>`).join("")}</ul>
        </div>
    `;
    }
});
