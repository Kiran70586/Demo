

const toggleSidebar = () =>
{
	
    if($(".sidebar").is(":visible"))
    {
        $(".sidebar").css("display","none");
        $(".content").css("margin-left","0%");
        console.log("hello");
    }
    else
    {
        
        $(".sidebar").css("display","block");
        $(".content").css("margin-left","20%");
    }
};

		function delete1(id)
		{
			Swal.fire({ title: "Are you sure?", text: "You won't be able to revert this!", icon: "warning", showCancelButton: true, confirmButtonColor: "#3085d6", cancelButtonColor: "#d33", confirmButtonText: "Yes, delete it!" })
			.then((result) => { if (result.isConfirmed)
			 { window.location="/user/delete/"+id; Swal.fire({ title: "Deleted!", text: "Your file has been deleted.", icon: "success" }); } });
		}
