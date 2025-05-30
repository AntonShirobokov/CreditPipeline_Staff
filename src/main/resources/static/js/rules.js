document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("ruleForm");
    const responseMessage = document.getElementById("responseMessage");
    const rulesList = document.getElementById("rulesList");

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

    // Загрузка и отображение правил
    async function loadRules() {
        try {
            const response = await fetch("/api/rules/getAllRules");
            if (!response.ok) throw new Error("Ошибка загрузки правил");

            const rules = await response.json();
            rulesList.innerHTML = "";

            if (rules.length === 0) {
                rulesList.innerHTML = "<p>Правила отсутствуют.</p>";
                return;
            }

            rules.forEach(rule => {
                const card = document.createElement("div");
                card.className = "rule-card";

                card.innerHTML = `
                    <div class="rule-info">
                        <div class="field">${fieldLabel(rule.fieldName)}</div>
                        <div class="condition">${formatOperation(rule.operation)} ${rule.value}</div>
                        <div class="reason">${rule.reason}</div>
                    </div>
                    <button class="delete-btn" data-id="${rule.id}">Удалить</button>
                `;

                const deleteBtn = card.querySelector(".delete-btn");
                deleteBtn.addEventListener("click", () => deleteRule(rule.id));

                rulesList.appendChild(card);
            });

        } catch (error) {
            rulesList.innerHTML = `<p style="color: red;">Ошибка: ${error.message}</p>`;
        }
    }

    // Удаление правила
    async function deleteRule(ruleId) {
        try {
            const response = await fetch("/api/rules/deleteRule", {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify(ruleId)
            });

            if (!response.ok) throw new Error("Не удалось удалить правило");

            loadRules(); // Обновить список
        } catch (error) {
            alert("Ошибка удаления: " + error.message);
        }
    }

    // Форматирование операций
    function formatOperation(op) {
        switch (op) {
            case "LESS_THAN": return "<";
            case "GREATER_THAN": return ">";
            case "EQUALS": return "=";
            default: return op;
        }
    }

    // Отображение названия поля по ключу
    function fieldLabel(fieldKey) {
        const labels = {
            c_age: "Возраст",
            c_income: "Доход",
            c_dept: "Задолженность",
            c_real_income: "Реальный доход",
            c_number_of_dependents: "Кол-во иждивенцев",
            c_type_of_employment: "Тип занятости",
            c_was_bankrupt: "Банкротство",
            c_type_of_housing: "Тип жилья",
            c_marital_status: "Семейное положение",
            c_education: "Образование",
            c_deposit: "Залог"
        };
        return labels[fieldKey] || fieldKey;
    }

    // Отправка формы
    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const rule = {
            fieldName: document.getElementById("field").value,
            operation: document.getElementById("operation").value,
            value: document.getElementById("value").value,
            reason: document.getElementById("reason").value
        };

        try {
            const response = await fetch("/api/rules/addRule", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    [csrfHeader]: csrfToken
                },
                body: JSON.stringify(rule)
            });

            if (response.ok) {
                responseMessage.textContent = "Правило успешно добавлено!";
                responseMessage.style.color = "green";
                form.reset();
                loadRules();
            } else {
                const errorData = await response.json();
                responseMessage.textContent = "Ошибка: " + errorData.detail;
                responseMessage.style.color = "red";
            }
        } catch (error) {
            responseMessage.textContent = "Ошибка отправки: " + error.message;
            responseMessage.style.color = "red";
        }
    });

    loadRules();
});
