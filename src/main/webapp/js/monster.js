var width = 960
var height = 550
var aspect = width / height,
    chart = $(".canvas");
$(window).on("resize", function() {
    var targetWidth = chart.parent().width();
    chart.attr("width", targetWidth);
    chart.attr("height", targetWidth / aspect);
});
var canvas = d3.select(".canvas")

var radius = 10
//canvas.append("circle")
//  .attr("cx", width/2 - radius/2)
//  .attr("cy", height/2 - radius/2)
//  .attr("r", radius);

var test_arms = {
  "first":[1,2,3],
  "second":[1,2,3],
  "third":[1,2,3],
  "fourth":[1,2,3],
  "fifth":[1,2,3],
  "sixth":[1,2,3]
};

var keys = Object.keys(test_arms);

var length = keys.length;

var index = 1;
var rect_width = 210;
var rect_height = 180;
var initial_x_offset = $(window).width()/2 + rect_width/2;
var initial_y_offset = $(window).height()/2 + rect_height/2;
Object.keys(test_arms).map(function(key) {
  canvas.append('svg:image')
    .attr("xlink:href",'../images/arm_small.gif')
    .attr("width",rect_width)
    .attr("height",rect_height)
    .attr("transform","rotate(" + (index*-360/length) + ",100,220)") 
    .on("mouseover",function(){alert("fuck");});
    //.attr("transform","translate(" + initial_x_offset + "," + initial_y_offset + ") rotate(" + (index*-360/length) + ")");
    index++;
});
  canvas.append('svg:image')
    .attr("xlink:href",'../images/jupiter.gif')
    .attr("width",200)
    .attr("height",200)
    .attr("transform","translate(0,120)");
