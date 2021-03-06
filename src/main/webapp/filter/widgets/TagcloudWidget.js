(function ($) {

AjaxSolr.TagcloudWidget = AjaxSolr.AbstractFacetWidget.extend({
  afterRequest: function () {
    if (this.manager.response.facet_counts.facet_fields[this.field] === undefined) {
      $(this.target).html('No hay elementos con los filtros aplicados');
      return;
    }

    var maxCount = 0;
    var objectedItems = [];
    for (var facet in this.manager.response.facet_counts.facet_fields[this.field]) {
      if(facet!=''){
        var count = parseInt(this.manager.response.facet_counts.facet_fields[this.field][facet]);
        if (count > maxCount) {
          maxCount = count;
        }
        
        var description = facet.trim();
        /*
        var reg = /^\d+$/;
        if(reg.test(description)){
            description = description.substring(1,4)+"***"+description.substring(7,facet.length)
        }
        */
        objectedItems.push({ facet: facet, description: description, count: count });
      }
    }
    objectedItems.sort(function (a, b) {
      return a.facet < b.facet ? -1 : 1;
    });

    $(this.target).empty();
    

    var filtered = false;

    if(objectedItems.length==1){
    	if(this.manager.store.get('q').val().indexOf(this.field)!== -1 ){
	      $(this.target).append(
	  	        $('<a href="#" class="tagcloud_item label label-primary"></a>')
	  	        .text(description).append('<sub>('+objectedItems[i].count+')</sub>')
	  	        .addClass('label-danger tagcloud_size_10')
	  	        .click(function () {
		    	    self.manager.store.get('q').val('*:*');
		    	    self.doRequest();
		    	    return false;
		    	  })
	  	      );
	      filtered = true;
    	}else{
    		var fq = this.manager.store.values('fq');
    		for (var i = 0, l = fq.length; i < l; i++) {
    			if(fq[i].indexOf(this.field)!== -1){
    			      $(this.target).append(
    				  	        $('<a href="#" class="tagcloud_item label label-primary"></a>')
    				  	        .text(description+" [X]")
    				  	        .addClass('label-danger tagcloud_size_10')
    				  	        .click(this.removeFacet(fq[i]))
    				  	      );
    			      filtered = true;
    			}
    		}
    	}

/*
    	var fq = this.manager.store.values('fq');
    	for (var i = 0, l = fq.length; i < l; i++) {
    	  links.push($('<a href="#"></a>').text('(x) ' + fq[i]).click(self.removeFacet(fq[i])));
    	}
  */  	
    }
    
    if(!filtered)
	    for (var i = 0, l = objectedItems.length; i < l; i++) {
	      $(this.target).append(
	        $('<a href="#" class="tagcloud_item label label-primary"></a>')
	        .text(objectedItems[i].description).append('<sub>('+objectedItems[i].count+')</sub>')
	        .addClass('tagcloud_size_' + parseInt(objectedItems[i].count / maxCount * 10))
	        .click(this.clickHandler(objectedItems[i].facet))
	      );
	    }
  },
  removeFacet: function (facet) {
	    var self = this;
	    return function () {
	      if (self.manager.store.removeByValue('fq', facet)) {
	        self.doRequest();
	      }
	      return false;
	    };
	  }

});

})(jQuery);


