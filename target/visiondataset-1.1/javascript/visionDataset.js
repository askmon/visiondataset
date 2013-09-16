$(document).ready(function() {

	$("img").each(function(index, element) {
		var image = new Image();
		image.src = $(element).attr("src");
		if (!image.complete) {
			image.onload = positionFooter;
		}
	});

	$(window).bind('resize', positionFooter);

	positionFooter();
	$("#footer").toggle();

});

function positionFooter() {
	var footer = $("#footer");
	var footerHeight = footer.outerHeight(true);
	var height = $("#header").outerHeight(true) + $("#menu").outerHeight(true)
			+ $("#content").outerHeight(true);
	if (height > $(window).height() - footerHeight) {
		footer.css("position", "relative");
	} else {
		footer.css("position", "absolute");
	}
}

function animateMenu() {
	$(document).ready(function() {
		if ($(".actionMenu ul li").length == 0) {
			$(".actionMenu").hide();
		} else {
			$("div.section").css("padding-left", 80);
			$(".actionMenu ul").hide();
			$(".actionMenu .menuHandler").bind("mouseenter", showMenu);
			$(".actionMenu").bind("mouseleave", hideMenu);
		}
	});
}

function showMenu() {
	$(".actionMenu .menuHandler").hide();
	$(".actionMenu ul").show("slide");
}

function hideMenu() {
	$(".actionMenu ul").hide("slide", function() {
		$(".actionMenu .menuHandler").fadeIn();
	});
}

function showTags(doNotAnimate) {
	if (doNotAnimate) {
		$("table.tags").show();
		$(".tagOption.hide").show();
		$(".tagOption.show").hide();
		positionFooter();
	} else {
		$("table.tags").fadeIn(positionFooter);
		$(".tagOption").toggle();
	}
	$.cookie("showTags", true);
}

function hideTags() {
	$("table.tags").fadeOut(positionFooter);
	$(".tagOption").toggle();
	$.cookie("showTags", null);
}

function showScript() {
    $(".scriptBox").show();
    positionFooter();
    $.cookie("showScript", true);
}

function showAnnotations(doNotAnimate) {
	if (doNotAnimate) {
		$("table.annotations").show();
		$("div.annotation").show();
		$(".annotationOption.hide").show();
		$(".annotationOption.show").hide();
		positionFooter();
	} else {
		$("table.annotations").fadeIn(positionFooter);
		$("div.annotation").fadeIn(positionFooter);
		$(".annotationOption").toggle();
	}
	$.cookie("showAnnotations", true);
}

function hideAnnotations() {
	$("table.annotations").fadeOut(positionFooter);
	$("div.annotation").fadeOut(positionFooter);
	$(".annotationOption").toggle();
	$.cookie("showAnnotations", null);
}

function showAttachments(doNotAnimate) {
	if (doNotAnimate) {
		$("table.attachments").show();
		$(".attachmentOption.hide").show();
		$(".attachmentOption.show").hide();
		positionFooter();
	} else {
		$("table.attachments").fadeIn(positionFooter);
		$("#uriContainer").fadeIn(positionFooter);
		$(".attachmentOption").toggle();
	}
	$.cookie("showAttachments", true);
}

function showScript(){
    $(".scriptBox").toggle();
}

function showUriLink() {
	$(".uriLink").toggle();
}
function hideUriLink() {
	$(".uriLink").toggle();
}


function hideAttachments() {
	$("table.attachments").fadeOut(positionFooter);
	$(".attachmentOption").toggle();
	$.cookie("showAttachments", null);
}

function confirmDelete(text, objectId, actionLink) {
	$("#confirmDeleteDialog").dialog( {
		title : text + " " + objectId + "?",
		modal : true,
		buttons : {
			OK : function() {
				window.location = actionLink;
			},
			Cancel : function() {
				$(this).dialog('close');
			}
		}
	});
}

function permissionsForm(name) {
	$(document).ready(
			function() {

				$("#" + name + "_album_viewPermission100").bind(
						'change',
						function() {
							$("#" + name + "_album_createPermission10").attr(
									'checked', true);
						});

				$("#" + name + "_album_createPermission0").bind(
						'change',
						function() {
							if ($("#" + name + "_album_viewPermission100")
									.attr('checked')) {
								$("#" + name + "_album_viewPermission10").attr(
										'checked', true);
							}
						});

			});
}

function restrictedPermissions(name, elementId) {
	$(document).ready(
			function() {

				var view = $("#" + name + "_album_viewPermission100").attr(
						'checked');
				var add = $("#" + name + "_album_createPermission10").attr(
						'checked');
				if (view || add) {
					$("#" + elementId).show();
				} else {
					$("#" + elementId).hide();
				}
				positionFooter();

				$(":radio").bind(
						'change',
						function() {
							var view = $(
									"#" + name + "_album_viewPermission100")
									.attr('checked');
							var add = $(
									"#" + name + "_album_createPermission10")
									.attr('checked');
							if (view || add) {
								$("#" + elementId).show();
							} else {
								$("#" + elementId).hide();
							}
							positionFooter();
						});
			});
}

function dragNDropUsers(idList1, idList2) {
	$(document).ready(function() {
		$("#" + idList1 + " li").draggable( {
			'helper' : 'clone'
		});
		$("#" + idList2 + " li").draggable( {
			'helper' : 'clone'
		});
		$("#" + idList1).droppable( {
			hoverClass : 'drophover',
			drop : dropCB
		});
		$("#" + idList2).droppable( {
			hoverClass : 'drophover',
			drop : dropCB
		});
	});
}

function dragNDropContent(idSourcesContainer, idDestinations) {
	$(document).ready(function() {
		$(".subAlbumHandler,  .miniThumbnailHandler").draggable( {
			helper : "clone"
		});

		$(".dndContainer").droppable( {
			hoverClass : 'drophover',
			drop : dropCB,
			greedy : true
		});
	});
}

function dropCB(event, ui) {
	var item = ui.draggable;
	var source = item.parent()[0];
	var target = event.target;

	if (source == target) {
		return;
	}

	item.detach();
	item.appendTo(target);

	$(":checkbox:checked").each(function(index, value) {
		var other = $($(value).parent()[0]);
		other.detach();
		other.appendTo(target);
	});

	positionFooter();

}

function getIdsUsersPermission(name) {
	if ($("#users").is(':visible')) {
		var idsUsersPermission = [];
		$.each($("#usersWithPermission").children(), function(index, value) {
			idsUsersPermission.push(parseInt(value.id));
		});
		$("#" + name + "_idsUsersPermission").val(idsUsersPermission.join(","));
	}
}

function getIdsContentMoved() {

	var imgsDest = [];

	$.each($(".miniThumbnailHandler"), function(index, value) {
		var parentId = $(value).parent()[0].id;
		if (parentId != "currentAlbum") {
			imgsDest.push(value.id + ":" + parentId);
		}
	});

	$("#MoveAlbumContent_idsImagesDestinations").val(imgsDest.join(","));

	var albumsDest = [];

	$.each($(".subAlbumHandler"), function(index, value) {
		var parentId = $(value).parent()[0].id;
		if (parentId != "currentAlbum") {
			albumsDest.push(value.id + ":" + parentId);
		}
	});

	$("#MoveAlbumContent_idsAlbunsDestinations").val(albumsDest.join(","));

}

function selectAll() {
	$(":checkbox").each(function() {
		this.checked = true;
	});
}

function selectNone() {
	$(":checkbox").each(function() {
		this.checked = false;
	});
}

function autocomplete(selector, availableTags, multiple, genMultiTags) {

	// Adapted from http://jqueryui.com/demos/autocomplete/#multiple
	if (multiple) {
		$(selector).autocomplete(
				{
					minLength : 1,
					source : function(request, response) {
						// delegate back to autocomplete, but extract the last
						// term
						response($.ui.autocomplete.filter(availableTags,
								extractLastForAutocomplete(request.term)));
					},
					focus : function() {
						// prevent value inserted on focus
						return false;
					},
					select : function(event, ui) {
						var terms = splitForAutocomplete(this.value);
						// remove the current input
						terms.pop();
						// add the selected item
						terms.push(ui.item.value);
						// add placeholder to get the comma-and-space at the end
						terms.push("");
						this.value = terms.join(", ");
						if (genMultiTags) {
							// update generate tags
							generateMultiTags();
						}
						return false;
					}
				});
	} else {
		$(selector).autocomplete( {
			minLength : 1,
			source : availableTags
		});
	}
}

function splitForAutocomplete(val) {
	return val.split(/,\s*/);
}

function extractLastForAutocomplete(term) {
	return splitForAutocomplete(term).pop();
}

function getAnnotationCoords(formName) {
	var annotation = $("#annotationContainer .annotation");
	$("#" + formName + "_x").val(parseInt(annotation.css("left")));
	$("#" + formName + "_y").val(parseInt(annotation.css("top")));
	$("#" + formName + "_width").val(parseInt(annotation.css("width")));
	$("#" + formName + "_height").val(parseInt(annotation.css("height")));
}

function showAnnotation(annotationId) {
	$("#" + annotationId).addClass("selected");
}

function hideAnnotation(annotationId) {
	$("#" + annotationId).removeClass("selected");
}

function multiTags() {

	$("#tags").bind("keyup", generateMultiTags);

	// drop on content
	$(".albumHandler, .subAlbumHandler, .miniThumbnailHandler").droppable( {
		hoverClass : 'drophover',
		drop : dropTagCB
	});

}

function generateMultiTags() {
	$("#selectedTags").empty();
	$.each($("#tags").val().split(","), populateMultiTags);

	// drag tags
	$("#selectedTags li").draggable( {
		helper : "clone"
	});
}

function populateMultiTags(index, element) {
	var tag = element.trim();
	if (tag.length != 0) {
		$("#selectedTags").append("<li>" + tag + "</li>");
	}
}

function dropTagCB(event, ui) {
	var item = ui.draggable;
	var target = event.target;

	var tagName = item.text();

	addIfNotPresent($(target).find("ul"), tagName);

	$(":checkbox:checked").each(function(index, value) {
		var other = $($(value).parent()[0]);
		addIfNotPresent(other.find("ul"), tagName);
	});

	positionFooter();

}

function addIfNotPresent(object, tagName) {
	var present = false;
	$(object).find("li").each(function(index, value) {
		if ($(value).text() == tagName) {
			present = true;
		}
	});
	if (!present) {
		$(object)
				.append(
						"<li>"
								+ "<img title=\"Remove this tag from content\" src=\"img/icons/remove.png\" onclick=\"removeParent(this);\"/>"
								+ tagName + "</li>");
	}
}

function removeParent(object) {
	$(object).parent().remove();
}

function getTagsContent() {

	var imgsTags = [];

	$.each($("#destinations .miniThumbnailHandler"), function(index, value) {

		var tags = [];

		$.each($(value).find("li"), function(index_i, value_i) {
			tags.push($(value_i).text());
		});

		if (tags.length != 0) {
			imgsTags.push(value.id + ":" + tags.join(","));
		}

	});

	$("#MultiTag_imagesTags").val(imgsTags.join("##"));

	var albumsTags = [];

	$.each($("#destinations .albumHandler, #destinations .subAlbumHandler"),
			function(index, value) {

				var tags = [];

				$.each($(value).find("li"), function(index_i, value_i) {
					tags.push($(value_i).text());
				});

				if (tags.length != 0) {
					albumsTags.push(value.id + ":" + tags.join(","));
				}

			});

	$("#MultiTag_albunsTags").val(albumsTags.join("##"));

}
