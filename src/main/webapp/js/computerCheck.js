$(document).ready(
    function() {
        $( "#datepicker1" ).datepicker({ dateFormat: 'yy-mm-dd' });
        $( "#datepicker2" ).datepicker({ dateFormat: 'yy-mm-dd' });

        $("#addForm").bootstrapValidator({
            framework: 'bootstrap',

            fields: {
                computerName: {
                    validators: {
                        notEmpty: {
                            message: 'The computer must have a name :\'('
                        }
                    }
                }
            }
        })
    }
);