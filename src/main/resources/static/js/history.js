document.addEventListener('DOMContentLoaded', function () {
    const container = document.querySelector('.main-content');

    fetch('http://localhost:8081/api/history/getAllEmployeeApplication')
        .then(response => response.json())
        .then(data => {
            if (data.length === 0) {
                container.innerHTML += '<p>Нет обработанных заявок.</p>';
                return;
            }

            data.forEach(application => {
                const card = document.createElement('div');
                card.className = 'card';

                const fio = `${application.user.lastName} ${application.user.firstName[0]}. ${application.user.middleName[0]}.`;

                const isApproved = application.status === 'Одобрено';
                const refusalReason = application.reasonForRefusal?.trim();
                const statusText = isApproved
                    ? `Одобрена (ставка: ${application.percentageRate}%)`
                    : `Отклонена (причина: ${refusalReason ? refusalReason : 'не указана'})`;

                const info = `
                    <p><strong>Заявка №${application.id}</strong></p>
                    <p>ФИО: ${fio}</p>
                    <p>Цель: ${application.purpose}</p>
                    <p>Сумма: ${application.amount.toLocaleString()} ₽</p>
                    <p>Залог: ${application.deposit}</p>
                    <p>Срок: ${application.period} мес.</p>
                    <p><strong>Статус:</strong> ${statusText}</p>
                    <button class="details-btn">Подробнее</button>
                    <div class="details" style="display: none;"></div>
                `;

                card.innerHTML = info;

                const detailsBtn = card.querySelector('.details-btn');
                const detailsDiv = card.querySelector('.details');

                detailsBtn.addEventListener('click', () => {
                    if (detailsDiv.style.display === 'none') {
                        detailsDiv.style.display = 'block';
                        detailsBtn.textContent = 'Скрыть';

                        detailsDiv.innerHTML = `
                            <table class="details-table">
                                <caption>Основные данные</caption>
                                <tr><td><strong>ФИО</strong></td><td>${application.user.lastName} ${application.user.firstName} ${application.user.middleName}</td></tr>
                                <tr><td><strong>Телефон</strong></td><td>${application.user.phone}</td></tr>
                                <tr><td><strong>Возраст</strong></td><td>${application.age}</td></tr>
                                <tr><td><strong>Образование</strong></td><td>${application.education}</td></tr>
                                <tr><td><strong>Семейное положение</strong></td><td>${application.maritalStatus}</td></tr>
                                <tr><td><strong>Тип занятости</strong></td><td>${application.typeOfEmployment}</td></tr>
                                <tr><td><strong>Доход</strong></td><td>${application.income.toLocaleString()} ₽</td></tr>
                                <tr><td><strong>Недвижимость</strong></td><td>${application.deposit}</td></tr>
                                <tr><td><strong>Тип жилья</strong></td><td>${application.typeOfHousing}</td></tr>
                                <tr><td><strong>Иждивенцы</strong></td><td>${application.numberOfDependents}</td></tr>
                                <tr><td><strong>Скор</strong></td><td>${application.score}</td></tr>
                                <tr><td><strong>Цель кредита</strong></td><td>${application.purpose}</td></tr>
                                <tr><td><strong>Период</strong></td><td>${application.period} мес.</td></tr>
                                <tr><td><strong>Был банкротом</strong></td><td>${application.wasBankrupt ? 'Да' : 'Нет'}</td></tr>
                                <tr><td><strong>Дата создания</strong></td><td>${new Date(application.dateOfCreation).toLocaleString()}</td></tr>
                                <tr><td><strong>Дата обработки</strong></td><td>${new Date(application.dateOfReview).toLocaleString()}</td></tr>
                            </table>

                            <table class="details-table">
                                <caption>Паспортные данные</caption>
                                <tr><td><strong>Серия и номер</strong></td><td>${application.user.passport.series} ${application.user.passport.number}</td></tr>
                                <tr><td><strong>Дата рождения</strong></td><td>${application.user.passport.birthDate}</td></tr>
                                <tr><td><strong>Место рождения</strong></td><td>${application.user.passport.birthPlace}</td></tr>
                                <tr><td><strong>Кем выдан</strong></td><td>${application.user.passport.issuedBy}</td></tr>
                                <tr><td><strong>Дата выдачи</strong></td><td>${application.user.passport.issueDate}</td></tr>
                                <tr><td><strong>Код подразделения</strong></td><td>${application.user.passport.departmentCode}</td></tr>
                                <tr><td><strong>ИНН</strong></td><td>${application.user.passport.inn}</td></tr>
                                <tr><td><strong>СНИЛС</strong></td><td>${application.user.passport.snils}</td></tr>
                                <tr><td><strong>Адрес регистрации</strong></td><td>${application.user.passport.registrationAddress}</td></tr>
                            </table>
                        `;
                    } else {
                        detailsDiv.style.display = 'none';
                        detailsBtn.textContent = 'Подробнее';
                    }
                });

                container.appendChild(card);
            });
        })
        .catch(error => {
            console.error('Ошибка при получении истории заявок:', error);
            container.innerHTML += '<p>Не удалось загрузить историю заявок.</p>';
        });
});
