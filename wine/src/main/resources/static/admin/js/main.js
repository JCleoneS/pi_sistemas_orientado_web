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
        var ratingValue = output, rounded = (ratingValue | 0);
        slider.oninput = function() {
            for (var j = 0; j < 5 ; j++) {
                $("#demo").append(
                       '<i class="fa '+ ((j < rounded)
                                ? "fa-star"
                                : ((((ratingValue - j) > 0) && ((ratingValue - j) < 1))
                                   ? "fa-star-half-o"
                                   : "fa-star-o"))
                       +'" aria-hidden="true"></i>');
            }
        }

    function readURL(input) {
        if(input.files && input.files[0]){
            var reader = new FileReader();
            reader.onload = function(e){
                $('#imgPreview').attr('src', e.target.result).width(100).height(100);
            }
            reader.readAsDataURL(input.files[0])
        }
    }
    $('#productImage'.change(function(){
        readURL(this);
    });
    $(".custom-file-input").on("change", function(){
        var fileName = $(this).val().split("\\").pop();
        $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
    });