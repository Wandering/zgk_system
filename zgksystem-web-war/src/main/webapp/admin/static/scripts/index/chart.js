define(function(require, exports, module) {
	function Chart(options) {
		this.options = options.options;
		this.ele = options.ele ? document.getElementById(options.ele) : '';
		this.handle = options.handle;
		this.myChart = null;
	}

	Chart.prototype = {
		constructor: Chart,
		draw:function() {
			if (!this.ele) {
				return;
			}
			this.createChart();
		},
		initOptions: function() {
			return {
				title: {
					text: this.options.title || '',
					textStyle: {
						color: '#fff'
					}
				},
				tooltip: {
					trigger: 'axis',
					backgroundColor: 'rgba(255,255,255,0.5)'
				},
				legend: {
					x: 'center',
					y: 'bottom',
					data: this.options.legendData || []
				},
				toolbox: {
					show: false
				},
				calculable: true,
				xAxis: [{
					type: 'category',
					boundaryGap: true,
					data: this.options.xAxisData || [],
					axisLabel: {
						textStyle: {
							color: '#fff'
						}
					}
				}],
				yAxis: [{
					type: 'value',
					axisLabel: {
						formatter: '{value} °C',
						textStyle: {
							color: '#fff'
						}
					}
				}],
				series: this.options.seriesData || []
			}
		},
		createChart: function() {
			var that = this;
			that.myChart = echarts.init(that.ele, 'macarons');
			window.resize = that.myChart.resize;
			that.myChart.setOption(that.initOptions());
			that.myChart.on('click', function(param) {
				if (typeof that.handle === 'function') {
					that.handle(param);
				}
			});
		},
		refresh:function() {
			this.myChart = echarts.init(this.ele, 'macarons');
			this.myChart.setOption(this.initOptions());
		}
	}
	module.exports = Chart;
});