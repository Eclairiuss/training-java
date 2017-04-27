
$( function() {
    $( "#datepicker1" ).datepicker({ dateFormat: 'yy-mm-dd' });
    $( "#datepicker2" ).datepicker({ dateFormat: 'yy-mm-dd' });
});
$("#addForm").validate({
    rules: {
        computerName: "required",
        datepicker1: {
            required: false,
            date: true
        },
        datepicker2: {
            required: false,
            date: true
        }
    }
});