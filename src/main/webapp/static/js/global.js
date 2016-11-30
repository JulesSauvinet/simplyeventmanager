$(function(){
    $(".btn-delete-entity").click(function(e){
        if(!confirm("Confirmez vous la suppression? ")){
            e.preventDefault();
            return false;
        }
    });
});
