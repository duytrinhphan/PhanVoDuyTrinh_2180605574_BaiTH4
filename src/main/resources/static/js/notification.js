document.addEventListener('DOMContentLoaded', function() {
    setTimeout(function() {
        var alert = document.querySelector('.alert');
        if (alert) {
            alert.classList.remove('show');
            alert.classList.add('hide');
        }
    }, 3000);
});
