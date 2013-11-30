(function() {
	// Setup Main LK namespace
	var LK = LK || {};

	// Test module
	LK.test = (function() {
		// init method
		var init = function() {
			console.log("test");
			init2("test2");
		};

		var init2 = function(message) {
			console.log(message);
		};

		// Return public methods
		return {
			init : init
		};
	})();

	LK.common = (function() {
		var init = function(url) {
			expandListGroup();
			typeaheadInit();
		};
		
		var openPage = function(url) {
			$('body').append("<form id='redirect' class='hidden' method='GET' action='"+url+"'></form>");
			$('#redirect').submit();
		};

		var createGUID = function() {
			var guid = Math.random().toString(36).substring(7);
			var guid2 = Math.random().toString(36).substring(7);
			return "guid-"+guid+"-"+guid2;
		};
		
		var expandListGroup = function() {
			$("a.expand-cta").on("click",function (e) {
			    e.preventDefault();
			    $(this).siblings(".hide").removeClass("hide");
			    $(this).addClass("hide");
			    $(this).removeClass("active");
			    $(this).next().focus().blur();
			});
		};
		
		var typeaheadInit = function() {
			
			var typeahead = $('input.typeahead');
			if (typeahead.length) {
				typeahead.each(function( index ) {
					console.log($(this));
					var remoteDir = $(this).attr("data-remote");
					var type = $(this).attr("data-type");
					var limit = $(this).attr("data-limit");
					
					var template = "";
					if(type=="castle") {
						template = template + "<p class=\"id\">{{id}}</p>";
						template = template + "<p class=\"name\">{{value}}</p>";
						template = template + "<p class=\"player\">{{player}}</p>";
						template = template + "<p class=\"alliance\">{{alliance}}</p>";
					} else if(type=="player") {
						template = template + "<p class=\"id\">{{id}}</p>";
						template = template + "<p class=\"name\">{{value}}</p>";
						template = template + "<p class=\"alliance\">{{alliance}}</p>";
					} else if(type=="alliance") {
						template = template + "<p class=\"id\">{{id}}</p>";
						template = template + "<p class=\"name\">{{value}}</p>";
					} else {
						template = template + "<p class=\"id\">{{id}}</p>";
					}
					
					$(this).typeahead({
						name: type,
						limit: limit,
						remote: remoteDir,
						template: template,
						engine: Hogan
					});
				});
			};
		};
		
		// Return public methods
		return {
			init : init,
			openPage : openPage,
			createGUID : createGUID,
			expandListGroup : expandListGroup,
			typeaheadInit : typeaheadInit
		}
	})();
	
	// Displays and updates from logs the progress of a particular job 
	LK.readLogs = (function() {
		
		var logId = "";
		var maxSteps = 1;
		var maxStepsForPercentage = 0;
		var interval;
		
		var init = function() {
			var gatherLogsList = $('ul.list-group.gatherLogs');
			if (gatherLogsList.length) {
				logId = gatherLogsList.attr("data-job");
				maxSteps = gatherLogsList.attr("data-max-steps");
				initialiseProgressBar(gatherLogsList);
				gather();
				interval = setInterval(gather, 5000);
			}
		};

		var initialiseProgressBar = function(gatherLogsList) {
			var minSteps = 1;
			var actualPercentage = Math.ceil((minSteps / maxSteps) * 100);
			
			var progressHTML = "";
			progressHTML = progressHTML + "<div class=\"progress progress-striped active\">";
			progressHTML = progressHTML + "  <div class=\"progress-bar\"  role=\"progressbar\" aria-valuenow=\""+actualPercentage+"\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: "+actualPercentage+"%\">";
			progressHTML = progressHTML + "    <span class=\"sr-only\">"+actualPercentage+"% Complete</span>";
			progressHTML = progressHTML + "  </div>";
			progressHTML = progressHTML + "</div>";

			gatherLogsList.prepend(progressHTML);
			
		};
		
		var gather = function() {
			$.ajax({
				url: "/gather-data/log/"+logId+"/",
				dataType: "json",
				timeout: 4000,
				success: function(json) {
					
					var gatherLogsList = $('ul.list-group.gatherLogs');
					var step = parseInt(json.step);
					var detail = json.detail;
					var status = json.status;
					gatherLogsList.children(".list-group-item.step").each(function( index ) {
						var indexPlus = index+1;
						var currentStep = $(this).attr("data-step");
						$(this).removeClass("active");
						$(this).children("span.badge").empty();
						
						if(step > indexPlus) {
							$(this).addClass("old");
							$(this).children("span.badge").append("Complete");
						} else if (step == indexPlus) {
							if(status != "COMPLETE" && status != "ERROR") {
								$(this).addClass("active");	
							}
							$(this).children("span.badge").append(detail);
						}
					});
					
					var updatePercentage = Math.ceil(((step-1) / maxSteps) * 100);
					
					var progressBar = $(gatherLogsList).find(".progress-bar");
					$(progressBar).attr("aria-valuenow", updatePercentage)
					$(progressBar).css("width",updatePercentage+"%")
					$(progressBar).children("span.sr-only").html(updatePercentage + "% Complete")
					
					if(status == "COMPLETE") {
						clearInterval(interval);
						
						var updatePercentage = 100;
						$(progressBar).attr("aria-valuenow", updatePercentage)
						$(progressBar).css("width",updatePercentage+"%")
						$(progressBar).children("span.sr-only").html(updatePercentage + "% Complete")
						$(progressBar).addClass("progress-bar-success");
						$(progressBar).removeClass("active");
						$(progressBar).removeClass("progress-striped");
						
						var gatherLogsComplete = $('ul.list-group.gatherLogs li[data-step="complete"]');
						gatherLogsComplete.removeClass("hide");
						gatherLogsComplete.children("span.badge").append("Complete");
						
						var modalRequired = gatherLogsList.attr("data-complete-modal");
						if(modalRequired == "true") {
							modalId = gatherLogsList.attr("data-complete-modal-id");
							modalHeading = gatherLogsList.attr("data-complete-modal-heading");
							modalBody = gatherLogsList.attr("data-complete-modal-body");
							primaryLinkText = gatherLogsList.attr("data-complete-modal-primarylinktext");
							primaryLinkAction = gatherLogsList.attr("data-complete-modal-primarylinkaction");
							secondaryLinkText = gatherLogsList.attr("data-complete-modal-secondarylinktext");
							
							LK.modal.create(modalId,modalHeading,modalBody,primaryLinkText,primaryLinkAction,secondaryLinkText);
							
							$("#"+modalId).modal("show");
						}
						
					} else if (status == "ERROR") {
						clearInterval(interval);
						$(progressBar).addClass("progress-bar-danger");
						
						var gatherLogsError = $('ul.list-group.gatherLogs li[data-step="error"]');
						gatherLogsError.removeClass("hide");
						gatherLogsError.children("span.badge").append(detail);
						
						var modalRequired = gatherLogsList.attr("data-error-modal");
						if(modalRequired == "true") {
							modalId = gatherLogsList.attr("data-error-modal-id");
							modalHeading = gatherLogsList.attr("data-error-modal-heading");
							modalBody = gatherLogsList.attr("data-error-modal-body");
							primaryLinkText = gatherLogsList.attr("data-error-modal-primarylinktext");
							primaryLinkAction = gatherLogsList.attr("data-error-modal-primarylinkaction");
							secondaryLinkText = gatherLogsList.attr("data-error-modal-secondarylinktext");
							
							LK.modal.create(modalId,modalHeading,modalBody,primaryLinkText,primaryLinkAction,secondaryLinkText);
							
							$("#"+modalId).modal("show");
						}
					}
				},
				error: function(jqXHR, textStatus, error) {
					clearInterval(interval);
					alert("Error - Moving you to edit your details in a few seconds on in a few seconds");
					var err = textStatus;
					var errorDiv = $('ul.list-group.gatherLogs li[data-step="error"]');
					errorDiv.removeClass("hide");
					errorDiv.addClass("active");
					errorDiv.children("span.badge").append(err);
					
					var modalRequired = gatherLogsList.attr("data-error-modal");
					if(modalRequired == "true") {
						modalId = gatherLogsList.attr("data-error-modal-id");
						modalHeading = gatherLogsList.attr("data-error-modal-heading");
						modalBody = gatherLogsList.attr("data-error-modal-body");
						primaryLinkText = gatherLogsList.attr("data-error-modal-primarylinktext");
						primaryLinkAction = gatherLogsList.attr("data-error-modal-primarylinkaction");
						secondaryLinkText = gatherLogsList.attr("data-error-modal-secondarylinktext");
						
						LK.modal.create(modalId,modalHeading,modalBody,primaryLinkText,primaryLinkAction,secondaryLinkText);
						
						$("#"+modalId).modal("show");
					}
				}
			});
			
		};

		// Return public methods
		return {
			init : init
		};
	})();

	// Modal dialogs based on boostrap JS
	LK.modal = (function() {
		var create = function(id,heading,body,primaryLinkText,primaryLinkAction,secondaryLinktext) {
			
			var modalHTML = "";
			modalHTML = modalHTML + "<!-- Modal -->";
			modalHTML = modalHTML + "<div class=\"modal fade\" id=\""+id+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\""+id+"Label\" aria-hidden=\"true\">";
			modalHTML = modalHTML + "  <div class=\"modal-dialog\">";
			modalHTML = modalHTML + "    <div class=\"modal-content\">";
			modalHTML = modalHTML + "      <div class=\"modal-header\">";
			modalHTML = modalHTML + "        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>";
			modalHTML = modalHTML + "        <h4 class=\"modal-title\" id=\""+id+"Label\">"+heading+"</h4>";
			modalHTML = modalHTML + "      </div>";
			modalHTML = modalHTML + "      <div class=\"modal-body\">";
			modalHTML = modalHTML + "        "+body+"";
			modalHTML = modalHTML + "      </div>";
			modalHTML = modalHTML + "      <div class=\"modal-footer\">";
			modalHTML = modalHTML + "        <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">"+secondaryLinktext+"</button>";
			modalHTML = modalHTML + "        <button type=\"button\" class=\"btn btn-primary\">"+primaryLinkText+"</button>";
			modalHTML = modalHTML + "      </div>";
			modalHTML = modalHTML + "    </div><!-- /.modal-content -->";
			modalHTML = modalHTML + "  </div><!-- /.modal-dialog -->";
			modalHTML = modalHTML + "</div><!-- /.modal -->";
			
			$("body").append(modalHTML);
			
			$( "#"+id+" .btn-primary").click(function() {
				eval(primaryLinkAction);
			});
			
		};

		// Return public methods
		return {
			create : create //Example: LK.modal.create("id","heading","body","primaryLinkText","alert(\"clicked\")","secondaryLinktext");
		};
	})();
	
	
	
	LK.forms = (function() {
		var init = function() {
			loginInit();
			passwordInit();
			removeInit();
			searchCompareInit();
		};
		
		var loginInit = function() {
			var loginForm = $('div.login-form form.login-form');
			if (loginForm.length) {
				loginForm.find('input[type=checkbox]').change(function() {
					if($(this).is(':checked')) {
						var loginUrl = loginForm.attr('data-action-update-password');
						loginForm.attr('action', loginUrl);
					} else {
						var loginUrl = loginForm.attr('data-action-login');
						loginForm.attr('action', loginUrl);
					}
					
				});
			}
		};
		
		var passwordInit = function() {
			var passwordInputs = $('div.input-group input[type=password]').each(function( index ) {
				var $passwordInput = $(this);
				var $inputGroupBtnSpan = $(this).siblings("span.input-group-btn");
				if($inputGroupBtnSpan.length) {
					if(Modernizr.touch) {
						$inputGroupBtnSpan.find("button.btn").bind('touchstart',function() {
							$passwordInput.attr("type","text");
						})
						.bind('touchend',function() {
							$passwordInput.attr("type","password");
						});
					} else {
						$inputGroupBtnSpan.find("button.btn").bind('mousedown',function() {
							$passwordInput.attr("type","text");
						})
						.bind('mouseup mouseout mouseleave',function() {
							$passwordInput.attr("type","password");
						});
					}
				}
			});
		};
		
		var removeInit = function() {
			var removeButtons = $('form button.btn-remove').each(function() {
				
				var form = $(this).closest("form");
				var formId = form.attr("id");
				var jQueryPath = "form#" + formId + " button.btn-remove";
				var primaryLinkAction = "LK.forms.removeSubmit(\""+jQueryPath+"\");";
				var modalId = "modal-"+formId;
				var modalHeading = $(this).attr('data-modal-heading');
				var modalBody = $(this).attr('data-modal-body');
				var primaryLinkText = "Remove";
				var secondaryLinktext = "Cancel";
				
				// Create modal box
				LK.modal.create(modalId,modalHeading,modalBody,primaryLinkText,primaryLinkAction,secondaryLinktext);

				// Bind click to open modal
				$(this).click(function(event) {
					event.preventDefault();
					$("#"+modalId).modal("show");	
				});
			
			});
		};
		
		var removeSubmit = function(jQueryPath) {
			var removeButton = $(jQueryPath);
			var removeHidden = removeButton.siblings("input[type=hidden][name=remove]");
			if(removeHidden.length) {
				// just change the value of the field to true
				removeHidden.attr("value", "true");
			} else {
				// else add hidden element
				var removeHiddenHTML = "<input type=\"hidden\" value=\"true\" name=\"remove\" />";
				removeButton.after(removeHiddenHTML);
			}
			var form = removeButton.closest("form");
			form.submit();
		};
		
		var searchCompareInit = function() {
			var compareForm = $("form.compare");
			if (compareForm.length) {
				compareForm.each(function(index) {
					
					$(compareForm).find("input.typeahead").focus(function() {
						$(compareForm).find("input[name=searchPage]").val(1);
					});
					
					$(this).submit(function(event) {
						
						var url = $(this).attr('action');
						var compareRoot = $(this).attr('data-compare-root');
						var dataType = $(this).attr('data-type');
						var postData = $(this).serialize();
						event.preventDefault();
						
						$.ajax({
							url: url,
							dataType: "json",
							data: postData,
							timeout: 4000,
							success: function(json) {
								console.log(json);
								var resultsHtml = "";
								resultsHtml = resultsHtml + "<h3>Select to compare</h3>";
								resultsHtml = resultsHtml + "<p>Search '<b>"+json.query+"</b>' - Page "+json.page+" of "+json.totalPages+"</p>";
								
								resultsHtml = resultsHtml + "<div class=\"list-group\">";
								
								var results = json.results;
								var resultsLength = results.length;
								
								if (resultsLength == 1) {
									var url = compareRoot+"-"+results[0].id;
									console.log(url);
									window.location.href = url;
								} else {

									if(json.page != 1) {
										resultsHtml = resultsHtml + "<a class=\"list-group-item clearfix previous active\" href=\"#\">";
										resultsHtml = resultsHtml + "  View previous " + dataType + "s";
										resultsHtml = resultsHtml + "</a>";
									}
									
									$(results).each(function(i) {
										resultsHtml = resultsHtml + "<a class=\"list-group-item clearfix result\" href=\""+compareRoot+"-"+results[i].id+"\">";
										if(dataType == "player") {
											resultsHtml = resultsHtml + "  "+results[i].value+"<span class=\"badge\">"+results[i].alliance+"</span>";	
										} else if(dataType == "alliance") {
											resultsHtml = resultsHtml + "  "+results[i].value+"<span class=\"badge\">"+results[i].id+"</span>";	
										} else if(dataType == "castle") {
											resultsHtml = resultsHtml + "  "+results[i].value+"<span class=\"badge\">"+results[i].player+"</span>";	
										} else {
											resultsHtml = resultsHtml + "  "+results[i].value+"<span class=\"badge\">"+results[i].id+"</span>";
										}
										
										resultsHtml = resultsHtml + "</a>";
									});
									
									if(json.page != json.totalPages) {
										resultsHtml = resultsHtml + "<a class=\"list-group-item clearfix next active\" href=\"#\">";
										resultsHtml = resultsHtml + "  View next " + dataType + "s";
										resultsHtml = resultsHtml + "</a>";
									}
									resultsHtml = resultsHtml + "</div>";
									
									$(".search-results").html(resultsHtml);

									$(".search-results a.previous").click(function(event) {
										event.preventDefault();
										var searchForm = $(".search-form form");
										var searchPageInput = $(searchForm).find("input[name=searchPage]");
										var currentPage = $(searchPageInput).val();
										$(searchPageInput).val(parseInt(currentPage) - 1);
										$(searchForm).submit();
									});
									$(".search-results a.next").click(function(event) {
										event.preventDefault();
										var searchForm = $(".search-form form");
										var searchPageInput = $(searchForm).find("input[name=searchPage]");
										var currentPage = $(searchPageInput).val();
										$(searchPageInput).val(parseInt(currentPage) + 1);
										$(searchForm).submit();
									});
									
									$('.search-form input').blur();
								}
							

							},
							error: function(jqXHR, textStatus, error) {
								alert("There has been an error looking for "+dataType+"s to compare, please try again later");
							}
						});
					});
					
					
				});
			}
		};
		
		// Return public methods
		return {
			init : init,
			loginInit : loginInit,
			passwordInit : passwordInit,
			removeInit : removeInit,
			removeSubmit : removeSubmit,
			searchCompareInit : searchCompareInit 
		}
	})();
	
	LK.charts = (function() {
		// init method
		var init = function() {
			bindHighCharts();
		};

		var bindHighCharts = function() {
			var charts = $('div.highchart');
			if (charts.length) {
				charts.each(function( index ) {
					
					var dataDiv = $(this);
					
					var dataChartType = dataDiv.attr("data-chart-type");
					var dataTitle = dataDiv.attr("data-title");
					var dataSubtitle = dataDiv.attr("data-subtitle");

					var dataSeriesCount = dataDiv.attr("data-series-count");
					var dataSeriesTrackGrowth = dataDiv.attr("data-series-track-growth");
					
					var generatedId = "historic-data-" + LK.common.createGUID();
//					console.log(generatedId);
					dataDiv.attr("id",generatedId);
					
					var options = {
					    chart: {
					    	type: dataChartType,
					        renderTo: generatedId
					    },
					    title: {
					        text: dataTitle
					    },
					    subtitle: {
					        text: dataSubtitle
					    },
					    xAxis: {
					    },
					    yAxis: [],
					    plotOptions: {
			                line: {
			                    dataLabels: {
			                        enabled: true
			                    }
			                }
			            },
					    tooltip: {
					    	shared: true
					    },
					    legend: {
			                layout: 'vertical'
			            },
					    series: []
					};
					
					// transform the options if it's a pie chart
					if (dataChartType == 'pie') {
						delete options.xAxis;
						delete options.yAxis;
						delete options.plotOptions.line;
						delete options.tooltip.shared;
						
						options.plotOptions.pie = {
							allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: true,
			                    color: '#000000',
			                    connectorColor: '#000000',
			                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
			                }
						};
						
					} else {
					
						// Add data for last data series
						var i;
						for (i = 1; i <= dataSeriesCount; i++) {
	
							var seriesTitle = dataDiv.attr("data-series-" + i + "-title");
							var seriesType = dataDiv.attr("data-series-" + i + "-type");
	//						var seriesColor = dataDiv.attr("data-series-" + i + "-color");
							
							var yAxis = {
						    	labels: {
						            style: {
	//					            	color: seriesColor
						            }
						    	},
						        title: {
						            text: seriesTitle,
						            style: {
	//					            	color: seriesColor
						            }
						        },
						        allowDecimals: false
						    };
							
							var seriesReversed = dataDiv.attr("data-series-" + i + "-yaxis-reversed");
							if (seriesReversed !== undefined) {
								yAxis.reversed = true;
							}
							
							if (i > 1) {
	//							yAxis.min = 0;
								yAxis.opposite = true;
							};
							options.yAxis.push(yAxis);
						};
	
						// Set the xAxis type to date time if we're tracing growth (eg, dates)
						if(dataSeriesTrackGrowth !== undefined) {
							options.xAxis.type = "datetime";
							
						// Else add categories if we're not tracking growth (eg, dates)
						} else {
	//						var xAxisCategories = {
	//								categories: []
	//						};
							options.xAxis.categories = [];
							
							var dataCategories = $(this).find("data-categories");
							
							dataCategories.find("data-category").each(function(cindex) {
								var dataCategory = $(this);
								var dataCategoryValue = dataCategory.attr("data-category");
								options.xAxis.categories.push(dataCategoryValue);
							});
							
	//						options.xAxis.categories = xAxisCategories;
						};
					
					};
					// Add data series
					var dataSeriesObj = $(this).find("data-series");
					var dataSeriesObjCount = dataSeriesObj.length;
					dataSeriesObj.each(function(sindex) {
						
						var dataSeries = $(this);
						
						// Loop around each intended series for each data series (in reverse, so first is on top)
						for (i = dataSeriesCount; i >= 1; i--) {
							
							var seriesName = dataSeries.attr("data-series-" + i + "-name");
							var seriesType = dataDiv.attr("data-series-" + i + "-type");
//							var seriesColor = dataDiv.attr("data-series-" + i + "-color");
							
							var dataSeries = $(this); 
							
							var series = {
									data: [],
									name: seriesName,
									type: seriesType//,
//									color: seriesColor
							};
							
							if (i > 1) {
								var dataLabels = {
		                    			enabled: true
					                };
				                series.dataLabels = dataLabels;
								series.yAxis = 1;
							};
							
							//Do this if you want to track growth
							
							if(dataSeriesTrackGrowth !== undefined) {
								
								$(this).find("data-value").each(function(vindex) {
									var dateString = $(this).attr("data-date");
									var date = eval(dateString);
									var value = parseFloat($(this).attr("data-series-" + i + "-data"));
									var dataArray =  [date,value];
									series.data.push(dataArray);
								});
								
							} else if(dataChartType == 'pie') {
								var stepSize = 100/dataSeriesObjCount;
								var middleOfStepDeduction = stepSize/2;
								var hCenter = (stepSize*(sindex+1)) - middleOfStepDeduction;
								var vCenter = 50;
								
								series.center = [];
								series.center.push(hCenter + "%");
								series.center.push(vCenter + "%");
								
								$(this).find("data-value").each(function(vindex) {
									var dataArray = [];
									//console.log("getting values");
									
									var field = $(this).attr("data-series-1-data");
									//console.log(field);
									dataArray.push(field);
								
									var value = parseFloat($(this).attr("data-series-2-data"));
									//console.log(value);
									dataArray.push(value);
								
									//console.log("dataArray");
									//console.log(dataArray);
									series.data.push(dataArray);
									
								});
								
							} else {
								
								$(this).find("data-value").each(function(vindex) {
									// Default to get the float value
									var value = parseFloat($(this).attr("data-series-" + i + "-data"));
									series.data.push(value);									
								});
								
							}
							
							
							
							
							
							options.series.push(series);
							
						}
						
					});
					
					
					console.log(dataTitle);
					console.log(options);
					
					// Create the chart
				    var chart = new Highcharts.Chart(options);
					
					
					
				});
			};
			
			
		};

		// Return public methods
		return {
			init : init
		};
	})();
	
	// Test module
	LK.emoji = (function() {
		// init method
		var init = function() {
			initForElement(document.body);
		};
		var initForElement = function(element) {
			if (!navigator.userAgent.match(/(iPhone|iPod|iPad|Android|BlackBerry|IEMobile)/)) {
				emojiReplace(getLegitTextNodes(element));
			}
		};
		
		var getLegitTextNodes = function (element) {
			if (!document.createTreeWalker) return [];
			var blacklist = ['SCRIPT', 'OPTION', 'TEXTAREA'],
				textNodes = [],
				walker = document.createTreeWalker(
				    element,
				    NodeFilter.SHOW_TEXT,
				    function excludeBlacklistedNodes(node) {
				      if (blacklist.indexOf(node.parentElement.nodeName.toUpperCase()) >= 0) return NodeFilter.FILTER_REJECT;
				      if (String.prototype.trim && !node.nodeValue.trim().length) return NodeFilter.FILTER_SKIP;
				      return NodeFilter.FILTER_ACCEPT;
				    },
				    false
				);
			while(walker.nextNode()) textNodes.push(walker.currentNode);
			return textNodes;
		};
		
		var emojiReplace = function (nodes) {
			var PATTERN = /([\ud800-\udbff])([\udc00-\udfff])/g;
			nodes.forEach(function (node) {
				var replacement,
				value = node.nodeValue,
				matches = value.match(PATTERN);
				
				if (matches) {
					replacement = value.replace(PATTERN, function (match, p1, p2) {
			        	var newSpan = document.createElement('span');
		        		newSpan.textContent = match;
	        			newSpan.className = "android";
	    				return newSpan.outerHTML;
					});
		        node.parentNode.replaceChild(fragmentForString(replacement), node);
				}
			});
		};
		
		var fragmentForString = function (htmlString) {
			var tmpDoc = document.createElement('DIV'),
			fragment = document.createDocumentFragment(),
			childNode;
			tmpDoc.innerHTML = htmlString;
			while(childNode = tmpDoc.firstChild) {
				fragment.appendChild(childNode);
			}
			return fragment;
		};
		
		// Return public methods
		return {
			init : init,
			initForElement : initForElement
		};
	})();
	
	LK.maps = (function() {
		// init method
		var init = function() {
			mapAddToDom();
			mapCreatorFormInit();
		};
		
		var mapAddToDom = function() {
			var existingList = $(".list-group.map-existing");
			if (existingList.length) {
				var rootUrl = $(existingList).attr("data-root-url");
				var dividerAttribute = $(existingList).attr("data-divider-attribute");
				var dividerItem = $(existingList).attr("data-divider-item");
				var defaultThicknessCastle = $(existingList).attr("data-default-thickness-castle");
				var defaultThicknessPlayer = $(existingList).attr("data-default-thickness-player");
				var defaultThicknessAlliance = $(existingList).attr("data-default-thickness-alliance");
				var serverId = $(existingList).attr("data-server-id");
				var colorsString = $(existingList).attr("data-colors");
				
				var colors = colorsString.split(dividerAttribute);
				
				var data = {
						rootUrl: rootUrl,
						dividerAttribute: dividerAttribute,
						dividerItem: dividerItem,
						colors:colors,
						defaultThicknessCastle: defaultThicknessCastle,
						defaultThicknessPlayer: defaultThicknessPlayer,
						defaultThicknessAlliance: defaultThicknessAlliance,
						serverId: serverId,
						items: {
							castles: [],
							players: [],
							alliances: []
						}
				};
				
				var mapListItems = $(existingList).find(".list-group-item");
				var mapListItemsCount = mapListItems.length;
				data.items.count = mapListItemsCount;
				
				$(mapListItems).each(function(i) {
					var type = $(this).attr("data-type");
					var id = $(this).attr("data-id");
					var thickness = $(this).attr("data-thickness");
					var color = $(this).attr("data-color");
					var path = $(this).attr("data-path");
					var removeUrl = $(this).attr("data-remove-url");
					
					var dataItem = {
							type: type,
							id: id,
							thickness: thickness,
							color: color,
							path: path,
							removeUrl: removeUrl
					};

					if (type == "c") {
						data.items.castles.push(dataItem);	
					} else if (type == "p") {
						data.items.players.push(dataItem);	
					} else if (type == "a") {
						data.items.alliances.push(dataItem);	
					} 
					
				});
				
				LK.maps.data = data;
				
				$(".colorpalette").colorPalette()
				.on('selectColor', function(e) {					
					console.log(this);
					console.log(e);
					console.log(e.color);
					var mapItem = $(this).closest(".list-group-item");
					console.log(mapItem);
					
					var existingType = $(mapItem).attr("data-type");
					var existingId = $(mapItem).attr("data-id");
					var existingThickness = $(mapItem).attr("data-thickness");
					var existingColor = $(mapItem).attr("data-color");
					var newColor = e.color.substring(1).toLowerCase();
					removeMapItem(existingType,existingId,existingThickness,existingColor);
					addMapItem(existingType,existingId,existingThickness,newColor);
				});
				
			};
			
		};
		
		var removeMapItem = function(type,id,thickness,color) {
			console.log("remove:");
			console.log(type + "-" + id + "-" + thickness + "-" + color);
			
			if (type == "c") {
				for (var i=0;i<LK.maps.data.items.castles.length;i++)
				{
					var realId = LK.maps.data.items.castles[i].id;
					if(realId == id) {
						LK.maps.data.items.castles.splice(i, 1);
						break;
					}
				}
			} else if (type == "p") {
				for (var i=0;i<LK.maps.data.items.players.length;i++)
				{
					var realId = LK.maps.data.items.players[i].id;
					if(realId == id) {
						LK.maps.data.items.players.splice(i, 1);
						break;
					}
				}
			} else if (type == "a") {
				for (var i=0;i<LK.maps.data.items.alliances.length;i++)
				{
					var realId = LK.maps.data.items.alliances[i].id;
					if(realId == id) {
						LK.maps.data.items.alliances.splice(i, 1);
						break;
					}
				}
			}
		};
		
		var addMapItem = function(type,id,thickness,color) {
			console.log("add:");
			console.log(type + "-" + id + "-" + thickness + "-" + color);
			
			var dataItem = {
				type: type,
				id: id,
				thickness: thickness,
				color: color,
				path: type + "-" + id + "-" + thickness + "-" + color
			};
			
			if (type == "c") {
				LK.maps.data.items.castles.push(dataItem);	
			} else if (type == "p") {
				LK.maps.data.items.players.push(dataItem);	
			} else if (type == "a") {
				LK.maps.data.items.alliances.push(dataItem);	
			}
			
			var paths = [];
			$(LK.maps.data.items.alliances).each(function() {
				paths.push(this.path);
			});
			$(LK.maps.data.items.players).each(function() {
				paths.push(this.path);
			});
			$(LK.maps.data.items.castles).each(function() {
				paths.push(this.path);
			});
			
			path = paths.join(LK.maps.data.dividerItem);
			var url = LK.maps.data.rootUrl + "/" + LK.maps.data.serverId + "/" + path;
			window.location.href = url;
		};
		
		var mapCreatorFormInit = function() {
			var mapCreatorForm = $("form.map-creator");
			if (mapCreatorForm.length) {
				mapCreatorForm.each(function(index) {
					
					$(mapCreatorForm).find("input.typeahead").focus(function() {
						$(mapCreatorForm).find("input[name=searchPage]").val(1);
					});
					
					$(this).submit(function(event) {
						
						var searchForm = $(this);
						var url = $(searchForm).attr('action');
						var compareRoot = $(searchForm).attr('data-compare-root');
						var dataType = $(searchForm).attr('data-type');
						var postData = $(searchForm).serialize();
						event.preventDefault();
						
						$.ajax({
							url: url,
							dataType: "json",
							data: postData,
							timeout: 4000,
							success: function(json) {
								console.log(json);
								var resultsHtml = "";
								resultsHtml = resultsHtml + "<h3>Add to map</h3>";
								resultsHtml = resultsHtml + "<p>Search "+dataType+"s '<b>"+json.query+"</b>' - Page "+json.page+" of "+json.totalPages+"</p>";
								
								resultsHtml = resultsHtml + "<div class=\"list-group\">";
								
								var results = json.results;
								var resultsLength = results.length;
								
								if (resultsLength == 1) {
									
									var type = dataType.substring(0,1);
									var id = results[0].id;
									var thickness = 1;
									var color = LK.maps.data.colors[(LK.maps.data.items.count+1)];
									
									if(dataType == "player") {
										thickness = LK.maps.data.defaultThicknessPlayer;
									} else if(dataType == "alliance") {
										thickness = LK.maps.data.defaultThicknessAlliance;
									} else if(dataType == "castle") {	
										thickness = LK.maps.data.defaultThicknessCastle;
									}
									LK.maps.addMapItem(type,id,thickness,color);
									
								} else {

									if(json.page != 1) {
										resultsHtml = resultsHtml + "<a class=\"list-group-item clearfix previous active\" data-type=\""+dataType+"\" href=\"#\">";
										resultsHtml = resultsHtml + "  View previous " + dataType + "s";
										resultsHtml = resultsHtml + "</a>";
									}
									
									$(results).each(function(i) {
										
										var linkText = results[i].value+"<span class=\"badge\">"+results[i].id+"</span>";
										var thickness = 1;
										
										if(dataType == "player") {
											linkText = results[i].value+"<span class=\"badge\">"+results[i].alliance+"</span>";
											thickness = LK.maps.data.defaultThicknessPlayer;
										} else if(dataType == "alliance") {
											linkText = results[i].value+"<span class=\"badge\">"+results[i].id+"</span>";
											thickness = LK.maps.data.defaultThicknessAlliance;
										} else if(dataType == "castle") {
											linkText = results[i].value+"<span class=\"badge\">"+results[i].player+"</span>";	
											thickness = LK.maps.data.defaultThicknessCastle;
										}
										
										resultsHtml = resultsHtml + "<a class=\"list-group-item clearfix result\" href=\"#\"";
										resultsHtml = resultsHtml + "data-type=\""+dataType.substring(0,1)+"\"";
										resultsHtml = resultsHtml + "data-id=\""+results[i].id+"\"";
										resultsHtml = resultsHtml + "data-thickness=\""+thickness+"\"";
										resultsHtml = resultsHtml + "data-color=\""+LK.maps.data.colors[(LK.maps.data.items.count+1)]+"\"";
										resultsHtml = resultsHtml + ">";
										resultsHtml = resultsHtml + "  " + linkText;
										resultsHtml = resultsHtml + "</a>";
									});
									
									if(json.page != json.totalPages) {
										resultsHtml = resultsHtml + "<a class=\"list-group-item clearfix next active\" data-type=\""+dataType+"\" href=\"#\">";
										resultsHtml = resultsHtml + "  View next " + dataType + "s";
										resultsHtml = resultsHtml + "</a>";
									}
									resultsHtml = resultsHtml + "</div>";
									
									console.log(resultsHtml);
									
									$(".search-results").html(resultsHtml);

									$(".search-results a.previous").click(function(event) {
										event.preventDefault();
										var dataType = $(this).attr("data-type");
										var searchForm = $(".search-form form[data-type="+dataType+"]");
										var searchPageInput = $(searchForm).find("input[name=searchPage]");
										var currentPage = $(searchPageInput).val();
										$(searchPageInput).val(parseInt(currentPage) - 1);
										$(searchForm).submit();
									});
									$(".search-results a.next").click(function(event) {
										event.preventDefault();
										var searchForm = $(".search-form form[data-type="+dataType+"]");
										var searchPageInput = $(searchForm).find("input[name=searchPage]");
										var currentPage = $(searchPageInput).val();
										$(searchPageInput).val(parseInt(currentPage) + 1);
										$(searchForm).submit();
									});
									
									$(".search-results a.result").click(function(event) {
										event.preventDefault();
										var type = $(this).attr("data-type");
										var id = $(this).attr("data-id");
										var thickness = $(this).attr("data-thickness");
										var color = $(this).attr("data-color");
										LK.maps.addMapItem(type,id,thickness,color);
									});
									
									$('.search-form input').blur();
								}
							

							},
							error: function(jqXHR, textStatus, error) {
								alert("There has been an error looking for "+dataType+"s to compare, please try again later");
							}
						});
					});
					
					
				});
			}
		};
		
		// Return public methods
		return {
			init : init,
			addMapItem : addMapItem,
			removeMapItem : removeMapItem
		};
	})();
	
	window.LK = LK;

	// Main LK init method
	$(function() {
		LK.readLogs.init();
		LK.forms.init();
		LK.common.init();
		LK.emoji.init();
		LK.charts.init();
		LK.maps.init();
	});
})();