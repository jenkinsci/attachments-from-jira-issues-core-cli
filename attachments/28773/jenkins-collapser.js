(function(){

/**
	Collapse All step-like configuration containers.
*/
function collapseAll() {
	var els = document.querySelectorAll('.hetero-list-container>div');
	for (var i=0; i<els.length; i++) {
		var rows = els[i].querySelectorAll('tr');
		var collapser = getCollapser(els[i]);
		collapser(rows, els[i]);
	}
}

/**
	Get collapsing function for the container.
	
	@note Each collapser should take two parametrs: rows, container.
*/
function getCollapser(container) {
	var type = container.getAttribute('descriptorid');
	switch (type) {
		case 'hudson.scm.listtagsparameter.ListSubversionTagsParameterDefinition':
			return collapseToName;
		break;
		case 'hudson.plugins.copyartifact.BuildSelectorParameter':
		case 'com.cwctravel.hudson.plugins.extended_choice_parameter.ExtendedChoiceParameterDefinition':
			return function (rows, container) {
				collapseToNamedInput('_.name', rows, container);
			};
		break;
		case 'hudson.tasks.Shell':
			return collapseToShellAbstract;
		break;
	}
	if (typeof(type) === 'string' && type.search(/hudson\.model\.\w+ParameterDefinition/)===0) {
		return collapseToName;
	}
	// extension point: for-each extraCollapsers call extraCollapser.isMine and if true return extraCollapser.collapser
	return fullyCollapse;
}
/**
	Collapser: Fully collapse rows without info.
*/
function fullyCollapse(rows) {
	for (var r=1; r<rows.length; r++) {
		rows[r].style.display = 'none';
	}
}
/**
	Collapser: Get information from standard 'parameter.name' input.
*/
function collapseToName(rows, container) {
	collapseToNamedInput('parameter.name', rows, container);
}
/**
	Collapser-helper: Get information from given input.
*/
function collapseToNamedInput(name, rows, container) {
	fullyCollapse(rows);
	var names = container.querySelectorAll('input[name="'+name+'"]');
	if (names.length) {
		addCollapsedInfo(container, names[0].value);
	}
}
/**
	Collapser: Get information from textarea.
*/
function collapseToShellAbstract(rows, container) {
	fullyCollapse(rows);
	var names = container.querySelectorAll('textarea');
	if (names.length) {
		addCollapsedInfo(container, names[0].value
			.replace(/[\r\n]+(#|rem)\s+\n/g, '')	// comment without thext
			.replace(/[\r\n]+/g, '; ')	// implode new lines with ';'
		);
	}
}

/**
	Add abstracted information to containers handle.
	
	@note Assumes drag-and-drop handle will be left visible after collapsing.
*/
var maxInfoCharacters = 200;
function addCollapsedInfo(container, info) {
	var parent = container.querySelectorAll('.dd-handle');
	if (parent.length) {
		var infoEl = appendInfoElement(parent[0]);
		if (info.length > maxInfoCharacters) {
			info = info.substr(0, maxInfoCharacters);
			info += ' (...)';
		}
		infoEl.textContent = ' ['+info+']';
	}
}
/**
	Append or return existing information element.
	@private
*/
function appendInfoElement(parent) {
	var infoEl = parent.querySelectorAll('.collapsed-info');
	if (infoEl.length) {
		infoEl = infoEl[0];
	} else {
		infoEl = document.createElement('span');
		infoEl.className = 'collapsed-info';
		parent.appendChild(infoEl);
	}
	return infoEl;
}

// test
collapseAll();
})();