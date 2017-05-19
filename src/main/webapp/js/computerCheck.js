$(document).ready(
    // Date Picker
    function() {
        $('#datepicker1').datepicker({
            format : 'yyyy-mm-dd'
        }).on(
            'changeDate',
            function(e) {
                $('#ComputerForm').bootstrapValidator(
                    'revalidateField', 'introduced');
            });
        $('#datepicker2').datepicker({
            format : 'yyyy-mm-dd'
        }).on(
            'changeDate',
            function(e) {
                $('#ComputerForm').bootstrapValidator(
                    'revalidateField', 'discontinued');
            });

        $("#addForm")
            .bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons : {
                    valid : 'glyphicon glyphicon-ok',
                    invalid : 'glyphicon glyphicon-remove',
                    validating : 'glyphicon glyphicon-refresh'
                },
                fields : {
                    computerName : {
                        validators : {
                            notEmpty : {
                                message : 'The name is required'
                            },
                            regexp : {
                                regexp : /^[\w\-\_\s]+$/,
                                message : 'The name can only consist of alphanumerics, dashs, underscores and spaces'
                            }
                        }
                    },
                    introduced : {
                        validators : {
                            date : {
                                format : 'YYYY-MM-DD',
                                message : 'The value is not a valid date'
                            }
                        }
                    },
                    discontinued : {
                        validators : {
                            date : {
                                format : 'YYYY-MM-DD',
                                message : 'The value is not a valid date'
                            }
                        }
                    }
                }
            })
    });