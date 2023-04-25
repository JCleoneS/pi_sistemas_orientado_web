const inputs = document.querySelectorAll('.input');


function focusFunc(){
    let parent = this.parentNode;
    parent.classList.add('focus');

}

function blurFunc(){
    let parent = this.parentNode.parentNode;
    if(this.value==""){
        parent.classList.remove('focus');
    }
}

inputs.forEach(input => {
	input.addEventListener("focus", focusFunc);
	input.addEventListener("blur", blurFunc);
});

let search = document.querySelector('.search-box');

document.querySelector('#search-icon').onclick = () => {
    search.classList.toggle('active');
}

function setTwoNumberDecimal(event) {
    this.value = parseDouble(this.value).toFixed(2);
}


        var slider = document.getElementById("myRange");
        var output = document.getElementById("demo");
        output.innerHTML = slider.value / 2;

        slider.oninput = function() {
            output.innerHTML = this.value / 2;
        }


