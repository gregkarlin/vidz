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

var test_arms = {
  "first":[1,2,3],
  "second":[1,2],
  "third":[1,2,3,4],
  "fourth":[1,2],
  "fifth":[],
  "sixth":[1,2,3,4,5]
};

var keys = Object.keys(test_arms);

var length = keys.length;

var index = 1;
var rect_width = 210;
var rect_height = 180;
var offset = -15;
var initial_x_offset = $(window).width()/2 + rect_width/2;
var initial_y_offset = $(window).height()/2 + rect_height/2;
Object.keys(test_arms).map(function(key) {
  var position_index = index;
  var vid_length = test_arms[key].length;
  canvas.append('svg:image')
    .attr("xlink:href",'../images/arm_small.gif')
    .attr("width",rect_width)
    .attr("height",rect_height)
    .attr("transform","rotate(" + (offset + (index*-360/length)) + ",100,220)") 
    .on("mouseover",function(){showVideos(key,position_index,vid_length,offset);})
    .on("mouseout",function(){hideVideos(position_index);});
    index++;
});
canvas.append('svg:image')
    .attr("xlink:href",'../images/jupiter.gif')
    .attr("width",200)
    .attr("height",200)
    .attr("transform","translate(0,120)");
