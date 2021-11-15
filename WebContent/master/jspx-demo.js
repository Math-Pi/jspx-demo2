function sw(me, me1, me2, me3) {
	var invoker = document.getElementById('_Invoker').value;
	try {
		if (me && document.getElementById(me) != null && me == invoker) {
			document.getElementById(me).style.display = 'none';
			document.getElementById("loadingImg").style.display = 'block';
		}
		if (me1 && document.getElementById(me1) != null&& me1 == invoker) {
			document.getElementById(me1).style.display = 'none';
			document.getElementById("loadingImg").style.display = 'block';
		}
		if (me2 && document.getElementById(me2) != null&& me2 == invoker) {
			document.getElementById(me2).style.display = 'none';
			document.getElementById("loadingImg").style.display = 'block';
		}
		if (me3 && document.getElementById(me3) != null&& me3 == invoker) {
			document.getElementById(me3).style.display = 'none';
			document.getElementById("loadingImg").style.display = 'block';
		}
	} catch (e) {
	}
}

function updateAccount(accountVal) {
	var len = accountVal.length;
	var text = '';
	for ( var x = 4 - len; x > 0; x--) {
		text = '0' + text;
	}
	text = text + accountVal;
	document.getElementById("accountNum1").innerHTML = text;
}