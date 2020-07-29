function page(total, num, key) {
	//获得页面url的某个url参数的方法
	function getParameter(name) { 
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
		var r = window.location.search.substr(1).match(reg); 
		if (r!=null) return unescape(r[2]); return null;
	}

	//init
	$(function(){
		var totalPage = total;
		var totalRecords = num;
		var what = key;
		var pageNo = getParameter('page');
		if(!pageNo){
			pageNo = 1;
		}
		//生成分页
		//有些参数是可选的，比如lang，若不传有默认值
		kkpager.generPageHtml({
			//显示第几页
			pno : pageNo,
			//总页码
			total : totalPage,
			//总数据条数
			totalRecords : totalRecords,
			//链接前部
			hrefFormer : 'indexSearch.jsp?keys=',
			//链接尾部
			hrefLatter : what,
			getLink : function(n){
				return this.hrefFormer + this.hrefLatter + "&page="+n;
			}
			/* 
			,lang				: {
				firstPageText			: '首页',
				firstPageTipText		: '首页',
				lastPageText			: '尾页',
				lastPageTipText			: '尾页',
				prePageText				: '上一页',
				prePageTipText			: '上一页',
				nextPageText			: '下一页',
				nextPageTipText			: '下一页',
				totalPageBeforeText		: '共',
				totalPageAfterText		: '页',
				currPageBeforeText		: '当前第',
				currPageAfterText		: '页',
				totalInfoSplitStr		: '/',
				totalRecordsBeforeText	: '共',
				totalRecordsAfterText	: '条数据',
				gopageBeforeText		: ' 转到',
				gopageButtonOkText		: '确定',
				gopageAfterText			: '页',
				buttonTipBeforeText		: '第',
				buttonTipAfterText		: '页'
			}
			 */
			//,
			/* mode : 'click',//默认值是link，可选link或者click
			click : function(n){
				this.selectPage(n);
			  return false;
			} */
		});
	});
	
}