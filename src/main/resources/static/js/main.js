function deleteForm(event) {
    event.preventDefault();
    console.log("deleteForm function called");
    const form = document.getElementById("deleteForm");
    if(confirm("Would you like it to be deleted?")) {
        console.log("Form will be submitted");
        form.submit();
    } else {
        console.log("Form submission cancelled");
    }
}

        window.onload = () => {
            const isSearchModal = document.querySelector("#isSearchModal").value;
            if (JSON.parse(isSearchModal)) {
                searchModal.showModal();
            }

            document.querySelectorAll(".getActionBtn").forEach((btn) => {
                btn.addEventListener("click", (e) => {
                    e.preventDefault();
                    getAction(btn);
                });
            });

            document.querySelectorAll(".postActionBtn").forEach((btn) => {
                btn.addEventListener("click", (e) => {
                    e.preventDefault();
                    if (btn.classList.contains("deleteActionBtn")) {
                    deleteForm(e);
                    } else if (btn.classList.contains("updateActionBtn")) {
                    updateForm(e);
                    } else {
                    postAction(btn);
                    }
                });
            });
        }

        function postAction(button, callback = null) {
            const form = button.form;
            const params = getDefaultParam();
            const exParams = [];

            form.querySelectorAll("input").forEach((input) => {
                if(input.name !== null && input.type !== "submit") {
                    exParams.push(input.name);
                }
            });

            if(callback !== null) {
                callback(params);
            }
            Object.entries(params).forEach(([key, value]) => {
                if(exParams.includes(key)) {
                    return;
                }

                const input = document.createElement("input");
                input.setAttribute("type", "hidden");
                input.setAttribute("name", key);
                input.setAttribute("value", value);
                form.appendChild(input);
            });

            form.submit();
        }

        function getAction(link, callback = null) {
            const url = link.dataset.url;
            const params = getDefaultParam();

            if(callback !== null) {
                callback(params);
            }

            const queryString = new URLSearchParams(params).toString();
            location.href = url + '?' + queryString;
        }

        function getDefaultParam() {
            const paramInputList = document.querySelectorAll("#params input");

            const params = {};
            paramInputList.forEach((input) => {
                params[input.name] = input.value;
            });

            return params;
        }