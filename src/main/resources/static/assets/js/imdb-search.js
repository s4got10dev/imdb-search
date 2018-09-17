function search() {
    var query = $("#search-title").find("input[name=search]").val();
    $.get("/imdb/find-title?title=" + encodeURIComponent(query),
        function (data) {
            var t = $("table#results tbody").empty();
            if (!data) return;
            data.forEach(function (title) {
                $("<tr>" +
                    "<td class='titleId'><a href=\"https://www.imdb.com/title/" + title.titleId + "\" target=\"_blank\">" + title.titleId + "</a></td>" +
                    "<td>" + title.primaryTitle + "</td><td>" + title.genres + "</td>" +
                    "<td><div class=\"icon-container\" data-toggle=\"collapse\" data-target=\"#" + title.titleId + "\" class=\"accordion-toggle\">" +
                    "<span class=\"ti-angle-double-down\"></span></div></td></tr>" +
                    "<tr><td colspan=\"4\" class=\"hiddenRow\">" +
                    "<div class=\"accordian-body collapse\" id=\"" + title.titleId + "\"></div></td></tr>").appendTo(t)
            });

            $("table#results tbody tr").each(function () {
                $(this).children("td:last").click(function () {
                    getTitleDetails($(this).children(":first").attr("data-target"))
                })
            });
        }, "json");
    return false;
}

$("#search-title").submit(search);

function getTitleDetails(id) {
    if ($(id).html())
        return;
    $.get("http://www.omdbapi.com/?i=" + encodeURIComponent(id.substring(1)) + "&apikey=<YOUR_API_KEY_HERE>&plot=full",
        function (data) {1
            if (!data) return;
            var title = data;
            data.Response === "True" ?
                $(id).html(
                    "<div class=\"col-lg-2\"><div class=\"content\">" +
                    "<div class=\"image\"><img class=\"img-fluid\" alt=\"...\" src=\"" + title.Poster + "\"</img></div>" +
                    "</div></div>" +
                    "<div class=\"col-lg-7\"><div class=\"content\">" +
                    getLabelRow("Title:", title.Title) +
                    "<div class=\"row\"><label>Year:</label>" + title.Year + "<label>Released:</label>" + title.Released + "</div>" +
                    "<div class=\"row\"><label>DVD:</label>" + title.DVD + "<label>BoxOffice:</label>" + title.BoxOffice + "</div>" +
                    getLabelRow("Rated:", title.Rated) +
                    getLabelRow("Runtime:", title.Runtime) +
                    getLabelRow("Type:", title.Type) +
                    getLabelRow("Genre:", title.Genre) +
                    getLabelRow("Production:", title.Production) +
                    getLabelRow("Director:", title.Director) +
                    getLabelRow("Writer:", title.Writer) +
                    getLabelRow("Actors:", title.Actors) +
                    getLabelRow("Plot:", title.Plot) +
                    getLabelRow("Language:", title.Language) +
                    getLabelRow("Country:", title.Country) +
                    getLabelRow("Awards:", title.Awards) +
                    getLabelRow("Ratings:", title.Ratings.map(function (rating) {
                        return rating.Source + ":" + rating.Value
                    })) +
                    getLabelRow("Metascore:", title.Metascore) +
                    getLabelRow("IMDb Rating:", title.imdbRating) +
                    getLabelRow("IMDb number of Votes:", title.imdbVotes) +
                    "<div class=\"row\"><a href=\"" + title.titleId + "\" target=\"_blank\">Website</a></td>" +
                    "</div></div>"
                )
                : $(id).html("<div class=\"col-lg-7\"><div class=\"content row\">" +
                "<span class=\"label label-danger\">" + title.Error + "</span></div></div>");
        }, "json");
    return false;
}

function getLabelRow(label, value) {
    return "<div class=\"row\"><label>" + label + "</label>" + value + "</div>"
}
function resizeIframe(iframe) {
    iframe.height = iframe.contentWindow.document.body.scrollHeight + "px";
}