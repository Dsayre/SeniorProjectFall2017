
function searchTweets(){
	var keyword = document.getElementById("keywordTextbox").value;
	$.getJSON("http://thunderx.cise.ufl.edu:8080/api/s/".concat(keyword) , showResults);
}

function showResults(response){
  var results = response.results;
  /*var keyword = document.getElementById("keywordTextbox").value;
  results = results.filter(function (item) {
              return (item.title).indexOf(keyword) >= 0 | (item.text).indexOf(keyword) >= 0 |
              (item.date).indexOf(keyword) >= 0;
            });*/
  var rows = results.map(function(item){
    return createRow(item.title, item.image, item.date);
  });
  document.getElementById("apiList").innerHTML = "<input type='submit' value='Back' onclick='showSearchForm();' /><br /><table id='resultsTable'></table>";
  rows.forEach(function(row){
    document.getElementById("resultsTable").appendChild(row);
  });
  document.getElementById("searchForm").style.display = "none";
  document.getElementById("apiList").style.display = "block";
}

function showSearchForm() {
  document.getElementById("searchForm").style.display = "block";
  document.getElementById("apiList").style.display = "none";
}

function createRow(user, iconUrl, tweet){
  var tweetRow = document.createElement("tr");
  var iconCell = document.createElement("td");
  iconCell.setAttribute("class", "icon");
  var icon = document.createElement("img");
  icon.src = iconUrl;
  icon.setAttribute("alt", user);
  icon.setAttribute("height", 48);
  icon.setAttribute("width", 48);
  iconCell.appendChild(icon);
  tweetRow.appendChild(iconCell);
  var tweetCell = document.createElement("td");
  tweetCell.setAttribute("class", "tweet");
  tweetCell.appendChild(document.createTextNode(user + ": " + tweet));
  tweetRow.appendChild(tweetCell);
  return tweetRow;
}