document.addEventListener('DOMContentLoaded', function () {
    const container = document.querySelector('.main-content');

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('http://localhost:8081/api/evaluate/getAllUnresolvedApplication')
        .then(response => response.json())
        .then(data => {
            data.forEach(application => {
                const card = document.createElement('div');
                card.className = 'card';

                const fio = `${application.user.lastName} ${application.user.firstName[0]}. ${application.user.middleName[0]}.`;

                const info = `
                    <p><strong>Заявка №${application.id}</strong></p>
                    <p>ФИО: ${fio}</p>
                    <p>Цель: ${application.purpose}</p>
                    <p>Сумма: ${application.amount.toLocaleString()} ₽</p>
                    <p>Залог: ${application.deposit}</p>
                    <p>Срок: ${application.period} мес.</p>
                    <button class="approve">Одобрить</button>
                    <button class="danger">Отклонить</button>
                    <button class="details-btn">Подробнее</button>
                    <div class="details" style="display: none;"></div>
                `;
                card.innerHTML = info;

                const approveBtn = card.querySelector('.approve');
                const rejectBtn = card.querySelector('.danger');
                const detailsBtn = card.querySelector('.details-btn');
                const detailsDiv = card.querySelector('.details');

                let currentForm = null;
                let currentType = null;

                function closeCurrentForm() {
                    if (currentForm) {
                        currentForm.remove();
                        currentForm = null;
                        currentType = null;
                    }
                }

                approveBtn.addEventListener('click', () => {
                    if (currentType === 'approve') {
                        closeCurrentForm();
                        return;
                    }

                    closeCurrentForm();

                    const form = document.createElement('div');
                    form.className = 'approve-form';
                    form.innerHTML = `
                        <input type="number" step="0.01" min="0" placeholder="Процентная ставка" class="input-field">
                        <button class="submit-approve">Отправить</button>
                    `;

                    card.insertBefore(form, detailsDiv);

                    form.querySelector('.submit-approve').addEventListener('click', () => {
                        const rate = form.querySelector('input').value;
                        if (!rate) {
                            alert('Введите процентную ставку');
                            return;
                        }

                        fetch('http://localhost:8081/api/evaluate/approveApplication', {
                            method: 'PATCH',
                            headers: {
                                'Content-Type': 'application/json',
                                [csrfHeader]: csrfToken
                            },
                            body: JSON.stringify({
                                applicationId: application.id,
                                percentageRate: parseFloat(rate)
                            })
                        })
                            .then(response => {
                                if (!response.ok) throw new Error('Ошибка при отправке');
                                alert(`Заявка №${application.id} одобрена с процентной ставкой ${rate}%`);
                                card.remove();
                            })
                            .catch(error => {
                                console.error('Ошибка одобрения:', error);
                                alert('Произошла ошибка при одобрении заявки.');
                            });
                    });

                    currentForm = form;
                    currentType = 'approve';
                });

                rejectBtn.addEventListener('click', () => {
                    if (currentType === 'reject') {
                        closeCurrentForm();
                        return;
                    }

                    closeCurrentForm();

                    const form = document.createElement('div');
                    form.className = 'reject-form';
                    form.innerHTML = `
                        <input type="text" placeholder="Причина отказа" class="input-field">
                        <button class="submit-reject">Отправить</button>
                    `;

                    card.insertBefore(form, detailsDiv);

                    form.querySelector('.submit-reject').addEventListener('click', () => {
                        const reason = form.querySelector('input').value.trim();
                        if (!reason) {
                            alert('Укажите причину отказа');
                            return;
                        }

                        fetch('http://localhost:8081/api/evaluate/denyApplication', {
                            method: 'PATCH',
                            headers: {
                                'Content-Type': 'application/json',
                                [csrfHeader]: csrfToken
                            },
                            body: JSON.stringify({
                                applicationId: application.id,
                                reasonForRefusal: reason
                            })
                        })
                            .then(response => {
                                if (!response.ok) throw new Error('Ошибка при отправке');
                                alert(`Заявка №${application.id} отклонена по причине: "${reason}"`);
                                card.remove();
                            })
                            .catch(error => {
                                console.error('Ошибка отказа:', error);
                                alert('Произошла ошибка при отклонении заявки.');
                            });
                    });

                    currentForm = form;
                    currentType = 'reject';
                });

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
                                <tr><td><strong>Ставка</strong></td><td>${application.percentageRate} %</td></tr>
                                <tr><td><strong>Цель кредита</strong></td><td>${application.purpose}</td></tr>
                                <tr><td><strong>Период</strong></td><td>${application.period} мес.</td></tr>
                                <tr><td><strong>Был банкротом</strong></td><td>${application.wasBankrupt ? 'Да' : 'Нет'}</td></tr>
                                <tr><td><strong>Дата создания</strong></td><td>${new Date(application.dateOfCreation).toLocaleString()}</td></tr>
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
            console.error('Ошибка при получении заявок:', error);
        });
});
