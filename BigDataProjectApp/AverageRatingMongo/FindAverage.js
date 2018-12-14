var map = function(){
  emit(this.product_category,{
    count:1,
    star_rating:this.star_rating,
  });
};

var red = function(key,values){
  var redVal = {
    count:0,
    sum:0,
  }
  for(var i=0;i<values.length;i++){
    redVal.sum += values[i].star_rating;
    redVal.count+=values[i].count;
  }
  return redVal;
}

var fin = function(key,redVal){
  redVal.avg = redVal.sum/redVal.count;
  return redVal;
}

db.amazondata.mapReduce(map,
  red,
  {
    out:"average",
    finalize : fin
  }
)
