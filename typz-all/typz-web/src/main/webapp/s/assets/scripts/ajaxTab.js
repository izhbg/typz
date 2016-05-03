$(document).ready(function () {
	   ajax_version=true;
		$.ajaxSetup({
			cache: false
		});
		var container = $('.page-content');
	    var type = $("#fistPage").val();//window.location.href;
		if(ajax_version)
		{
		 if(type!=null&&type.trim()!=""){
			 setTimeout(function () {
		            loadPage(type, container)
		            $('ul.nav-list li:has(a[href="' + type + '"])').addClass('active').closest('.submenu').addClass('current').find('ul').css('display','block');
		        },
		      2000); 
		 }
	      
		$('.options-holder .fa-links').on('click',function(e){
		          e.preventDefault();
		               var url = $(this).attr('href');
		        var tab = $(this).attr('target');
		        if (tab == "_blank") {
		          window.open(url,'_blank');
		        } else if (url != "#") {
		          var container = $('.page-content');
		          loadPage(url, container);
		        }
		});
      $('ul.page-sidebar-menu li a').on('click', function (e) {
        e.preventDefault();
        //$('.main-content').load('index.html');
        var url = $(this).attr('href').trim();
        var tab = $(this).attr('target');
        if (tab == "_blank") {
          window.open(url,'_blank');
        } else if (url != "#") {
          var container = $('.page-content');
          $('ul.page-sidebar-menu li ').removeClass('active');
          $(this).parent().addClass('active');
          //$('.user-details').addClass('user-details-close');
          loadPage(url, container);
              
          if ($('.left-sidebar').hasClass('left-sidebar-open')) 
          {
          $('.left-sidebar').toggleClass('left-sidebar-open');
        }
        if ($('.site-holder').hasClass('mini-sidebar')) 
        {
          $('ul.nav-list li ul ').css('display','none');
      }
        }
        //$('.options-holder .col-sm-4').slideToggle('hidden');
      });


}
});
function loadPage(url, container) {
    urlExt = $("#contextpath").val()+"/"+url ;
      //console.log(container)
      $.ajax({
      	type: "GET",
      	url: urlExt,
      	dataType: 'html',
      	cache: false,
      	success: function () {
      		container.mask('<h1><i class="fa  fa-refresh fa-spin"></i> Loading...</h1>');
      		container.load(urlExt, null, function (responseText) {
               // window.location.hash =url;
                $('.breadcrumb .active').html(url);

  			//init();
                App.init(); // initlayout and core plugins
                Index.init();
  			//sortablePortlets();
      		}).fadeIn('slow');
      		//console.log("ajax request successful");
      	},
      	error: function (xhr, ajaxOptions, thrownError) {
      		//container.html('<h4 style="margin-top:10px; display:block; text-align:left"><i class="fa fa-warning txt-color-orangeDark"></i> Error 404! Page not found.</h4>');
                      container.load('404.html') ;
                      setTimeout(function () {
                      loadPage('dashboard', container)
                                $('ul.nav-list li ').removeClass('active');
                              $('ul.nav-list li:has(a[href="dashboard"])').addClass('active').closest('.submenu').addClass('current').find('ul').css('display','block');
                        },
                  3000);
            },
            async: false
          });
   }