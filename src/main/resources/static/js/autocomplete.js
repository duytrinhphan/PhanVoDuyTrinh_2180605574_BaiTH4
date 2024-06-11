$(document).ready(function() {
    $("#keyword").autocomplete({
        source: "/books/autocomplete",
        minLength: 2
    });
});
