document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("ruleForm");
    const responseMessage = document.getElementById("responseMessage");
    const rulesList = document.getElementById("rulesList");

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

    const fieldSelect = document.getElementById("field");
    const operationSelect = document.getElementById("operation");
    const valueContainer = document.getElementById("valueContainer");

    // Поля с 3 операциями
    const threeOpsFields = ["c_age", "c_income", "c_dept", "c_real_income", "c_number_of_dependents"];

    // Функция обновления доступных операций и поля "значение"
    function updateFormFields() {
        const selectedField = fieldSelect.value;

        // Очистить операции
        operationSelect.innerHTML = "";

        if (threeOpsFields.includes(selectedField)) {
            // Добавляем 3 операции
            operationSelect.innerHTML = `
                <option value="LESS_THAN">Меньше чем</option>
                <option value="GREATER_THAN">Больше чем</option>
                <option value="EQUALS">Равно</option>
            `;
        } else {
            // Для остальных только EQUALS
            operationSelect.innerHTML = `<option value="EQUALS">Равно</option>`;
        }

        // Обновляем поле для значения
        if (selectedField === "c_was_bankrupt") {
            // Заменить содержимое контейнера на select
            valueContainer.innerHTML = `
                <select id="value" name="value">
                    <option value="true">Был банкротом</option>
                    <option value="false">Не был банкротом</option>
                </select>
            `;
        } else {
            // Заменить содержимое контейнера на input
            valueContainer.innerHTML = `
                <input type="text" id="value" name="value" placeholder="Введите значение" required>
            `;
        }
    }

    // Изначальная инициализация
    updateFormFields();

    // Слушаем изменение поля выбора поля
    fieldSelect.addEventListener("change", () => {
        updateFormFields();
    });

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

        const valueElem = document.getElementById("value");
        let value = valueElem.value;

        const rule = {
            fieldName: fieldSelect.value,
            operation: operationSelect.value,
            value: value,
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
                updateFormFields(); // После reset надо восстановить поле value и операции
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
