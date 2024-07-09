
 window.onload = function() {
	updateTree();
};
function updateTree(){
    $("#tree").empty();
    $("#tree").unbind();
    $("#tree").treeview({
      tree: 100,
      url: "storage_app/tree" 
	});
}

// アイテム情報取得
function getItem(id){
	//アイテム情報のクリア
	clearItem();
	
	$.ajax({
		type		: "GET",
		url			: "storage_app/" + id,
		dataType	: "json",
		success		: function(data) {
						getItemSuccess(data);
					},
		error		: function(XMLHttpRequest, textStatus, errorThrown) {
						error(XMLHttpRequest, textStatus, errorThrown);
					}
	});
};

// アイテム情報取得 Ajax通信成功時処理
function getItemSuccess(data) {
	//alert("success:" + data);

	document.getElementById("itemName").value = data.name;
	document.getElementById("itemCategory").value = data.category;
	document.getElementById("itemNumber").value = data.number;
	document.getElementById("itemNote").value = data.note;
	document.getElementById("itemTag1").value = data.tag1;
	document.getElementById("itemTag2").value = data.tag2;
	document.getElementById("itemTag3").value = data.tag3;
	document.getElementById("pictureId").value = data.pictureId;
	document.getElementById("itemId").value = data.itemId;
	document.getElementById("parentItemId").value = data.parentItemId;
	document.getElementById("childNo").value = data.childNo;
	
	//アイテムの編集可否を切り替え
	if(data.parentItemId == 0){
		//ルートノードの場合は編集不可
		changeItemUpdateStatus(false);
	}
	else{
		changeItemUpdateStatus(true);
	}	
}

// アイテム情報登録
function updateItem(){
	//エラーメッセージのクリア
	clearErrorMessages();
	
	//登録するアイテムID、アイテム名の保持
	let updId = document.getElementById("itemId").value;
	let updName = document.getElementById("itemName").value;
	
	if(updId == ''){
		//新規登録
		$.ajax({
			type		: "POST",
			url			: "storage_app/insert",
			dataType	: "json",
			data        : $("form").serialize(),
			success		: function(data) {
							//alert("success:" + data);
							updId = data[0];
							let errors = data.slice(1);
							if(errors.length > 0){
								//エラーありの場合、エラーメッセージ表示
								displayErrorMessages(errors);
							}
							else{
								//エラーなしの場合、TreeViewの表示を最新化
								updateTree();
								
								//新規採番したアイテムIDを表示
								document.getElementById("itemId").value = updId;
							}
						},
			error		: function(XMLHttpRequest, textStatus, errorThrown) {
							error(XMLHttpRequest, textStatus, errorThrown);
						}
		});
	}
	else{
		//更新
		$.ajax({
			type		: "POST",
			url			: "storage_app/update",
			dataType	: "json",
			data        : $("form").serialize(),
			success		: function(data) {
							//alert("success:" + data);
							let errors = data;
							if(errors.length > 0){
								//エラーありの場合、エラーメッセージ表示
								displayErrorMessages(errors);
							}
							else{
								//エラーなしの場合、TreeView上のアイテム名を最新化
								document.querySelector('#n' + updId + ' span a').textContent = updName;
							}
						},
			error		: function(XMLHttpRequest, textStatus, errorThrown) {
							error(XMLHttpRequest, textStatus, errorThrown);
						}
		});
	}
};

// アイテム情報削除
function deleteItem(){
	$.ajax({
		type		: "POST",
		url			: "storage_app/delete",
		dataType	: "text",
		data        : {itemId : document.getElementById("itemId").value},
		success		: function(data) {
							//alert("success:" + data);
							updateTree();
							clearItem();
					},
		error		: function(XMLHttpRequest, textStatus, errorThrown) {
						error(XMLHttpRequest, textStatus, errorThrown);
					}
	});
};


// Ajax通信失敗時処理
function error(XMLHttpRequest, textStatus, errorThrown) {
	alert("error:" + XMLHttpRequest);
	alert("status:" + textStatus);
	alert("errorThrown:" + errorThrown);
}

//アイテム追加
function createItem(){
	//選択中のアイテムIDを親IDに設定
	parentItemId = document.getElementById("itemId").value;
	
	//アイテム登録フォームの初期化
	clearItem();
	document.getElementById("parentItemId").value = parentItemId;
	
	//アイテム情報を編集可にする
	changeItemUpdateStatus(true);
}

//アイテム登録フォームの初期化
function clearItem(){
	let elements = document.getElementById("itemForm").querySelectorAll("li input, textarea");
	for(let i = 0; i < elements.length; i++){
		elements[i].value = null;
	}
	
	//エラーメッセージのクリア
	clearErrorMessages();
}

//エラーメッセージのクリア
function clearErrorMessages(errors){
	let elements = document.querySelectorAll("#itemForm .error");
	for(let i = 0; i < elements.length; i++){
		elements[i].textContent = null;
	}
}

//エラーメッセージの表示
function displayErrorMessages(errors){
	for(let i = 0; i < errors.length; i++){
		var error = errors[i].split('：'); //error[0]:エラー項目 error[1]:エラーメッセージに分割
		document.getElementById('msg_' + error[0]).textContent = error[1];
	}
}

//アイテム情報の編集可否を切り替え（readOnly == trueの場合、編集可）
function changeItemUpdateStatus(canChange){
	if(canChange == true){
		readOnly = false;
		disabled = false;
		
		document.getElementById("itemName").classList.remove("readonly");
		document.getElementById("itemCategory").classList.remove("readonly");
		document.getElementById("itemNumber").classList.remove("readonly");
		document.getElementById("itemNote").classList.remove("readonly");
		document.getElementById("itemTag1").classList.remove("readonly");
		document.getElementById("itemTag2").classList.remove("readonly");
		document.getElementById("itemTag3").classList.remove("readonly");
		document.getElementById("pictureId").classList.remove("readonly");
	}
	else{
		readOnly = true;
		disabled = true;
		
		document.getElementById("itemName").classList.add("readonly");
		document.getElementById("itemCategory").classList.add("readonly");
		document.getElementById("itemNumber").classList.add("readonly");
		document.getElementById("itemNote").classList.add("readonly");
		document.getElementById("itemTag1").classList.add("readonly");
		document.getElementById("itemTag2").classList.add("readonly");
		document.getElementById("itemTag3").classList.add("readonly");
		document.getElementById("pictureId").classList.add("readonly");
	}
	
	document.getElementById("itemName").readOnly = readOnly;
	document.getElementById("itemCategory").readOnly = readOnly;
	document.getElementById("itemNumber").readOnly = readOnly;
	document.getElementById("itemNote").readOnly = readOnly;
	document.getElementById("itemTag1").readOnly = readOnly;
	document.getElementById("itemTag2").readOnly = readOnly;
	document.getElementById("itemTag3").readOnly = readOnly;
	document.getElementById("pictureId").readOnly = readOnly;
	
	document.getElementById("btnUpdate").disabled = disabled;
	document.getElementById("btnDelete").disabled = disabled;
}
