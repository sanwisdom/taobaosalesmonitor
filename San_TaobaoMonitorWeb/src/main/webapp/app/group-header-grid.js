Ext.require([
    'Ext.grid.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.state.*'
]);

Ext.onReady(function() {
    Ext.QuickTips.init();

    // sample static data for the store
//    var myData = [
//        ['3m Co',                               71.72, 0.02,  0.03,  '9/1 12:00am'],
//        ['Alcoa Inc',                           29.01, 0.42,  1.47,  '9/1 12:00am'],
//        ['Altria Group Inc',                    83.81, 0.28,  0.34,  '9/1 12:00am'],
//        ['American Express Company',            52.55, 0.01,  0.02,  '9/1 12:00am'],
//        ['American International Group, Inc.',  64.13, 0.31,  0.49,  '9/1 12:00am'],
//        ['AT&T Inc.',                           31.61, -0.48, -1.54, '9/1 12:00am'],
//        ['Boeing Co.',                          75.43, 0.53,  0.71,  '9/1 12:00am'],
//        ['Caterpillar Inc.',                    67.27, 0.92,  1.39,  '9/1 12:00am'],
//        ['Citigroup, Inc.',                     49.37, 0.02,  0.04,  '9/1 12:00am'],
//        ['E.I. du Pont de Nemours and Company', 40.48, 0.51,  1.28,  '9/1 12:00am'],
//        ['Exxon Mobil Corp',                    68.1,  -0.43, -0.64, '9/1 12:00am'],
//        ['General Electric Company',            34.14, -0.08, -0.23, '9/1 12:00am'],
//        ['General Motors Corporation',          30.27, 1.09,  3.74,  '9/1 12:00am'],
//        ['Hewlett-Packard Co.',                 36.53, -0.03, -0.08, '9/1 12:00am'],
//        ['Honeywell Intl Inc',                  38.77, 0.05,  0.13,  '9/1 12:00am'],
//        ['Intel Corporation',                   19.88, 0.31,  1.58,  '9/1 12:00am'],
//        ['International Business Machines',     81.41, 0.44,  0.54,  '9/1 12:00am'],
//        ['Johnson & Johnson',                   64.72, 0.06,  0.09,  '9/1 12:00am'],
//        ['JP Morgan & Chase & Co',              45.73, 0.07,  0.15,  '9/1 12:00am'],
//        ['McDonald\'s Corporation',             36.76, 0.86,  2.40,  '9/1 12:00am'],
//        ['Merck & Co., Inc.',                   40.96, 0.41,  1.01,  '9/1 12:00am'],
//        ['Microsoft Corporation',               25.84, 0.14,  0.54,  '9/1 12:00am'],
//        ['Pfizer Inc',                          27.96, 0.4,   1.45,  '9/1 12:00am'],
//        ['The Coca-Cola Company',               45.07, 0.26,  0.58,  '9/1 12:00am'],
//        ['The Home Depot, Inc.',                34.64, 0.35,  1.02,  '9/1 12:00am'],
//        ['The Procter & Gamble Company',        61.91, 0.01,  0.02,  '9/1 12:00am'],
//        ['United Technologies Corporation',     63.26, 0.55,  0.88,  '9/1 12:00am'],
//        ['Verizon Communications',              35.57, 0.39,  1.11,  '9/1 12:00am'],
//        ['Wal-Mart Stores, Inc.',               45.45, 0.73,  1.63,  '9/1 12:00am']
//    ];
    
    var myData1 = [
                  [1, 'http://img04.taobaocdn.com/bao/uploaded/i4/T1BTvRXnXmXXcTdjZU_015356.jpg_80x80.jpg', 'EXPLOIT 12V锂电池 多功能电动螺丝刀 充电式电钻 一年质保225021', 7, 219, 1533, 17334079355],
                  [2, 'http://img01.taobaocdn.com/bao/uploaded/i5/T1FJvRXbNkXXXRC5AV_020833.jpg_80x80.jpg', 'exploit/开拓 14-19寸家用五金多功能塑铁工具箱 706017', 17, 118, 1846, 6179519677],
                  [3, 'http://img02.taobaocdn.com/bao/uploaded/i6/T1RBjRXjdhXXc0Zx3U_015130.jpg_80x80.jpg', 'EXPLOIT 0.6-2.6mm 多功能 剥线钳 剥线器 电工工具 080201', 13, 18, 234, 6987078983],
                  [4, 'http://img01.taobaocdn.com/bao/uploaded/i1/T1xHjSXe0dXXbkbn7T_013250.jpg_80x80.jpg', 'exploit/开拓 PH0-PH3 台湾制造 十字螺丝刀 原装起子改锥 222870', 15, 27, 230, 13049443643],
                  [5, 'http://img02.taobaocdn.com/bao/uploaded/i6/T1UmHQXedoXXXw8SIU_015726.jpg_80x80.jpg', 'EXPLOIT 镍铁合金航空剪 铁网剪 铁皮剪刀 多规格 011502', 8, 28, 224, 6112822939],
                  [6, 'http://img02.taobaocdn.com/bao/uploaded/i6/T1Wj_SXbhbXXbT4vDX_084851.jpg_80x80.jpg', 'EXPLOIT 双头呆扳手 8-32MM 开口扳手 双开 两用扳手 020409', 11, 9, 144.5, 8188002515],
                  [7, 'http://img03.taobaocdn.com/bao/uploaded/i3/T1y5zRXcXiXXXnLuUT_011503.jpg_80x80.jpg', 'exploit/开拓 9件套 球头 内六角扳手套装 扳手工具 021203', 7, 15.5, 108.5, 7310612911],
                  [8, 'http://img04.taobaocdn.com/bao/uploaded/i4/T1y5PRXiJhXXXldiIU_014915.jpg_80x80.jpg', 'EXPLOIT 5M鲁班尺 风水尺 钢卷尺 家用装修风水测量特价220149', 12, 9, 108, 9079042935],
                  [9, 'http://img02.taobaocdn.com/bao/uploaded/i6/T1TGPSXjFdXXXp9IkV_020040.jpg_80x80.jpg', 'exploit 铁锤 迷你羊角锤子 榔头 汽车 破窗器 求生锤 052101', 7, 15, 105, 7003509873],
                  [10, 'http://img03.taobaocdn.com/bao/uploaded/i7/T1HZHSXh4fXXc8Gco4_052238.jpg_80x80.jpg', '开拓 多功能 帆布 PVC 小号 工具包 维修 电工包 工具箱 222220', 9, 6.8, 61.2, 8847997753],
                  [11, 'http://img01.taobaocdn.com/bao/uploaded/i5/T15DHRXhdfXXcG9SYb_124954.jpg_80x80.jpg', 'EXPLOIT 6"家用 尖嘴钳 钳子 五金工具 010111', 6, 10, 60, 8668268119],
                  [12, 'http://img03.taobaocdn.com/bao/uploaded/i7/T1sFvSXjheXXcqXFgU_013414.jpg_80x80.jpg', 'EXPLOIT 德国制造 进口 高强度 短平头 内六角扳手 工具 025701', 9, 3.8, 58, 7805424579],
                  [13, 'http://img04.taobaocdn.com/bao/uploaded/i4/T1PI_RXiBjXXbJ1Z.U_013914.jpg_80x80.jpg', 'EXPLOIT 3-8mm 十字螺丝刀 TPR方柄 螺丝批 改锥 多规格 040101', 8, 3, 43.5, 9751463529],
                  [14, 'http://img02.taobaocdn.com/bao/uploaded/i6/T1P.vRXb4eXXXWAYoT_011038.jpg_80x80.jpg', 'EXPLOIT 半透明热溶胶条 热熔胶条 热熔胶棒 多规格 220121', 40, 1, 40, 8036910499],
                  [15, 'http://img02.taobaocdn.com/bao/uploaded/i6/T10qrSXbteXXXjqhZU_015121.jpg_80x80.jpg', '开拓工具 多功能感应 测电笔 专业首选 比数显电笔更实用 222410', 6, 6.5, 39, 8935453183]
                  ];

    
    var myData = [
                  [1, 'http://img04.taobaocdn.com/bao/uploaded/i4/T1BTvRXnXmXXcTdjZU_015356.jpg_80x80.jpg', 'EXPLOIT 12V锂电池 多功能电动螺丝刀 充电式电钻 一年质保225021', 7, 219, 1533, 17334079355, 
                   'http://img04.taobaocdn.com/bao/uploaded/i4/T1BTvRXnXmXXcTdjZU_015356.jpg_80x80.jpg', 'EXPLOIT 12V锂电池 多功能电动螺丝刀 充电式电钻 一年质保225021', 7, 219, 1533, 17334079355, 
                   'http://img04.taobaocdn.com/bao/uploaded/i4/T1BTvRXnXmXXcTdjZU_015356.jpg_80x80.jpg', 'EXPLOIT 12V锂电池 多功能电动螺丝刀 充电式电钻 一年质保225021', 7, 219, 1533, 17334079355],
                  [2, 'http://img01.taobaocdn.com/bao/uploaded/i5/T1FJvRXbNkXXXRC5AV_020833.jpg_80x80.jpg', 'exploit/开拓 14-19寸家用五金多功能塑铁工具箱 706017', 17, 118, 1846, 6179519677, 
                   'http://img01.taobaocdn.com/bao/uploaded/i5/T1FJvRXbNkXXXRC5AV_020833.jpg_80x80.jpg', 'exploit/开拓 14-19寸家用五金多功能塑铁工具箱 706017', 17, 118, 1846, 6179519677, 
                   'http://img01.taobaocdn.com/bao/uploaded/i5/T1FJvRXbNkXXXRC5AV_020833.jpg_80x80.jpg', 'exploit/开拓 14-19寸家用五金多功能塑铁工具箱 706017', 17, 118, 1846, 6179519677],
                  [3, 'http://img02.taobaocdn.com/bao/uploaded/i6/T1RBjRXjdhXXc0Zx3U_015130.jpg_80x80.jpg', 'EXPLOIT 0.6-2.6mm 多功能 剥线钳 剥线器 电工工具 080201', 13, 18, 234, 6987078983,
                   'http://img02.taobaocdn.com/bao/uploaded/i6/T1RBjRXjdhXXc0Zx3U_015130.jpg_80x80.jpg', 'EXPLOIT 0.6-2.6mm 多功能 剥线钳 剥线器 电工工具 080201', 13, 18, 234, 6987078983,
                   'http://img02.taobaocdn.com/bao/uploaded/i6/T1RBjRXjdhXXc0Zx3U_015130.jpg_80x80.jpg', 'EXPLOIT 0.6-2.6mm 多功能 剥线钳 剥线器 电工工具 080201', 13, 18, 234, 6987078983],
                  [4, 'http://img01.taobaocdn.com/bao/uploaded/i1/T1xHjSXe0dXXbkbn7T_013250.jpg_80x80.jpg', 'exploit/开拓 PH0-PH3 台湾制造 十字螺丝刀 原装起子改锥 222870', 15, 27, 230, 13049443643,
                   'http://img01.taobaocdn.com/bao/uploaded/i1/T1xHjSXe0dXXbkbn7T_013250.jpg_80x80.jpg', 'exploit/开拓 PH0-PH3 台湾制造 十字螺丝刀 原装起子改锥 222870', 15, 27, 230, 13049443643,
                   'http://img01.taobaocdn.com/bao/uploaded/i1/T1xHjSXe0dXXbkbn7T_013250.jpg_80x80.jpg', 'exploit/开拓 PH0-PH3 台湾制造 十字螺丝刀 原装起子改锥 222870', 15, 27, 230, 13049443643],
                  [5, 'http://img02.taobaocdn.com/bao/uploaded/i6/T1UmHQXedoXXXw8SIU_015726.jpg_80x80.jpg', 'EXPLOIT 镍铁合金航空剪 铁网剪 铁皮剪刀 多规格 011502', 8, 28, 224, 6112822939,
                   'http://img02.taobaocdn.com/bao/uploaded/i6/T1UmHQXedoXXXw8SIU_015726.jpg_80x80.jpg', 'EXPLOIT 镍铁合金航空剪 铁网剪 铁皮剪刀 多规格 011502', 8, 28, 224, 6112822939,
                   'http://img02.taobaocdn.com/bao/uploaded/i6/T1UmHQXedoXXXw8SIU_015726.jpg_80x80.jpg', 'EXPLOIT 镍铁合金航空剪 铁网剪 铁皮剪刀 多规格 011502', 8, 28, 224, 6112822939],
                  [6, 'http://img02.taobaocdn.com/bao/uploaded/i6/T1Wj_SXbhbXXbT4vDX_084851.jpg_80x80.jpg', 'EXPLOIT 双头呆扳手 8-32MM 开口扳手 双开 两用扳手 020409', 11, 9, 144.5, 8188002515,
                   'http://img02.taobaocdn.com/bao/uploaded/i6/T1Wj_SXbhbXXbT4vDX_084851.jpg_80x80.jpg', 'EXPLOIT 双头呆扳手 8-32MM 开口扳手 双开 两用扳手 020409', 11, 9, 144.5, 8188002515,
                   'http://img02.taobaocdn.com/bao/uploaded/i6/T1Wj_SXbhbXXbT4vDX_084851.jpg_80x80.jpg', 'EXPLOIT 双头呆扳手 8-32MM 开口扳手 双开 两用扳手 020409', 11, 9, 144.5, 8188002515],
                  [7, 'http://img03.taobaocdn.com/bao/uploaded/i3/T1y5zRXcXiXXXnLuUT_011503.jpg_80x80.jpg', 'exploit/开拓 9件套 球头 内六角扳手套装 扳手工具 021203', 7, 15.5, 108.5, 7310612911,
                   'http://img03.taobaocdn.com/bao/uploaded/i3/T1y5zRXcXiXXXnLuUT_011503.jpg_80x80.jpg', 'exploit/开拓 9件套 球头 内六角扳手套装 扳手工具 021203', 7, 15.5, 108.5, 7310612911,
                   'http://img03.taobaocdn.com/bao/uploaded/i3/T1y5zRXcXiXXXnLuUT_011503.jpg_80x80.jpg', 'exploit/开拓 9件套 球头 内六角扳手套装 扳手工具 021203', 7, 15.5, 108.5, 7310612911],
                  [8, 'http://img04.taobaocdn.com/bao/uploaded/i4/T1y5PRXiJhXXXldiIU_014915.jpg_80x80.jpg', 'EXPLOIT 5M鲁班尺 风水尺 钢卷尺 家用装修风水测量特价220149', 12, 9, 108, 9079042935,
                   'http://img04.taobaocdn.com/bao/uploaded/i4/T1y5PRXiJhXXXldiIU_014915.jpg_80x80.jpg', 'EXPLOIT 5M鲁班尺 风水尺 钢卷尺 家用装修风水测量特价220149', 12, 9, 108, 9079042935,
                   'http://img04.taobaocdn.com/bao/uploaded/i4/T1y5PRXiJhXXXldiIU_014915.jpg_80x80.jpg', 'EXPLOIT 5M鲁班尺 风水尺 钢卷尺 家用装修风水测量特价220149', 12, 9, 108, 9079042935],
                  [9, 'http://img02.taobaocdn.com/bao/uploaded/i6/T1TGPSXjFdXXXp9IkV_020040.jpg_80x80.jpg', 'exploit 铁锤 迷你羊角锤子 榔头 汽车 破窗器 求生锤 052101', 7, 15, 105, 7003509873,
                   'http://img02.taobaocdn.com/bao/uploaded/i6/T1TGPSXjFdXXXp9IkV_020040.jpg_80x80.jpg', 'exploit 铁锤 迷你羊角锤子 榔头 汽车 破窗器 求生锤 052101', 7, 15, 105, 7003509873,
                   'http://img02.taobaocdn.com/bao/uploaded/i6/T1TGPSXjFdXXXp9IkV_020040.jpg_80x80.jpg', 'exploit 铁锤 迷你羊角锤子 榔头 汽车 破窗器 求生锤 052101', 7, 15, 105, 7003509873],
                  [10, 'http://img03.taobaocdn.com/bao/uploaded/i7/T1HZHSXh4fXXc8Gco4_052238.jpg_80x80.jpg', '开拓 多功能 帆布 PVC 小号 工具包 维修 电工包 工具箱 222220', 9, 6.8, 61.2, 8847997753,
                   'http://img03.taobaocdn.com/bao/uploaded/i7/T1HZHSXh4fXXc8Gco4_052238.jpg_80x80.jpg', '开拓 多功能 帆布 PVC 小号 工具包 维修 电工包 工具箱 222220', 9, 6.8, 61.2, 8847997753,
                   'http://img03.taobaocdn.com/bao/uploaded/i7/T1HZHSXh4fXXc8Gco4_052238.jpg_80x80.jpg', '开拓 多功能 帆布 PVC 小号 工具包 维修 电工包 工具箱 222220', 9, 6.8, 61.2, 8847997753],
                  [11, 'http://img01.taobaocdn.com/bao/uploaded/i5/T15DHRXhdfXXcG9SYb_124954.jpg_80x80.jpg', 'EXPLOIT 6"家用 尖嘴钳 钳子 五金工具 010111', 6, 10, 60, 8668268119,
                   'http://img01.taobaocdn.com/bao/uploaded/i5/T15DHRXhdfXXcG9SYb_124954.jpg_80x80.jpg', 'EXPLOIT 6"家用 尖嘴钳 钳子 五金工具 010111', 6, 10, 60, 8668268119,
                   'http://img01.taobaocdn.com/bao/uploaded/i5/T15DHRXhdfXXcG9SYb_124954.jpg_80x80.jpg', 'EXPLOIT 6"家用 尖嘴钳 钳子 五金工具 010111', 6, 10, 60, 8668268119],
                  [12, 'http://img03.taobaocdn.com/bao/uploaded/i7/T1sFvSXjheXXcqXFgU_013414.jpg_80x80.jpg', 'EXPLOIT 德国制造 进口 高强度 短平头 内六角扳手 工具 025701', 9, 3.8, 58, 7805424579,
                   'http://img03.taobaocdn.com/bao/uploaded/i7/T1sFvSXjheXXcqXFgU_013414.jpg_80x80.jpg', 'EXPLOIT 德国制造 进口 高强度 短平头 内六角扳手 工具 025701', 9, 3.8, 58, 7805424579,
                   'http://img03.taobaocdn.com/bao/uploaded/i7/T1sFvSXjheXXcqXFgU_013414.jpg_80x80.jpg', 'EXPLOIT 德国制造 进口 高强度 短平头 内六角扳手 工具 025701', 9, 3.8, 58, 7805424579],
                  [13, 'http://img04.taobaocdn.com/bao/uploaded/i4/T1PI_RXiBjXXbJ1Z.U_013914.jpg_80x80.jpg', 'EXPLOIT 3-8mm 十字螺丝刀 TPR方柄 螺丝批 改锥 多规格 040101', 8, 3, 43.5, 9751463529,
                   'http://img04.taobaocdn.com/bao/uploaded/i4/T1PI_RXiBjXXbJ1Z.U_013914.jpg_80x80.jpg', 'EXPLOIT 3-8mm 十字螺丝刀 TPR方柄 螺丝批 改锥 多规格 040101', 8, 3, 43.5, 9751463529,
                   'http://img04.taobaocdn.com/bao/uploaded/i4/T1PI_RXiBjXXbJ1Z.U_013914.jpg_80x80.jpg', 'EXPLOIT 3-8mm 十字螺丝刀 TPR方柄 螺丝批 改锥 多规格 040101', 8, 3, 43.5, 9751463529],
                  [14, 'http://img02.taobaocdn.com/bao/uploaded/i6/T1P.vRXb4eXXXWAYoT_011038.jpg_80x80.jpg', 'EXPLOIT 半透明热溶胶条 热熔胶条 热熔胶棒 多规格 220121', 40, 1, 40, 8036910499,
                   'http://img02.taobaocdn.com/bao/uploaded/i6/T1P.vRXb4eXXXWAYoT_011038.jpg_80x80.jpg', 'EXPLOIT 半透明热溶胶条 热熔胶条 热熔胶棒 多规格 220121', 40, 1, 40, 8036910499,
                   'http://img02.taobaocdn.com/bao/uploaded/i6/T1P.vRXb4eXXXWAYoT_011038.jpg_80x80.jpg', 'EXPLOIT 半透明热溶胶条 热熔胶条 热熔胶棒 多规格 220121', 40, 1, 40, 8036910499],
                  [15, 'http://img02.taobaocdn.com/bao/uploaded/i6/T10qrSXbteXXXjqhZU_015121.jpg_80x80.jpg', '开拓工具 多功能感应 测电笔 专业首选 比数显电笔更实用 222410', 6, 6.5, 39, 8935453183,
                   'http://img02.taobaocdn.com/bao/uploaded/i6/T10qrSXbteXXXjqhZU_015121.jpg_80x80.jpg', '开拓工具 多功能感应 测电笔 专业首选 比数显电笔更实用 222410', 6, 6.5, 39, 8935453183,
                   'http://img02.taobaocdn.com/bao/uploaded/i6/T10qrSXbteXXXjqhZU_015121.jpg_80x80.jpg', '开拓工具 多功能感应 测电笔 专业首选 比数显电笔更实用 222410', 6, 6.5, 39, 8935453183]
                  ];

    /**
     * Custom function used for column renderer
     * @param {Object} val
     */
    function change(val) {
        if (val > 0) {
            return '<span style="color:green;">' + val + '</span>';
        } else if (val < 0) {
            return '<span style="color:red;">' + val + '</span>';
        }
        return val;
    }

    /**
     * Custom function used for column renderer
     * @param {Object} val
     */
    function pctChange(val) {
        if (val > 0) {
            return '<span style="color:green;">' + val + '%</span>';
        } else if (val < 0) {
            return '<span style="color:red;">' + val + '%</span>';
        }
        return val;
    }
    

	function thumbChange(val) {
		val = val.replace(".jpg_160x160", ".jpg_80x80");
		return '<img src="' + val + '">';
	}

	function linkChange(val) {
		if (val != null) {
			return '<div style=" vertical-align: middle; line-height:100px;"><a href=\'http://detail.tmall.com/item.htm?id=' + val + '&;\'>' + val + '</a></div>';
		}
		return val;
	}
    
    function columnWrap(val){
        return '<div style=" white-space: pre-wrap!important; vertical-align: middle; margin: 10px; line-height:30px;">'+ val +'</div>';
    }
    // margin:10px!important; 
    
    function defaultRenderer(val) {
    	return '<div style=" vertical-align: middle; line-height:100px;">'+ val +'</div>'; // line-height:30px;
    }

    // create the data store
    var store1 = Ext.create('Ext.data.ArrayStore', {
        fields: [
           {name: 'top'},
           {name: 'thumbnail'},
           {name: 'productName'},
           {name: 'salesAmount',     type: 'int'},
           {name: 'unitPrice',  type: 'float'},
           {name: 'total',  type: 'float'},
           {name: 'productId'}
        ],
        data: myData1
    });
    
    var columnTemplate = [{
        column:{
            "dataIndex"	: 	"thumbnail",
            "name"		: 	"产品图",
            "width"		:	90,
            "sortable"	:	true,
            "renderer" 	:	thumbChange,
            "align"		:	"center",
            "type":"string"
        }
    },{
    	column:{
            "dataIndex"	: 	"productName",
            "name"		: 	"品名",
            "width"		:	175,
            "sortable"	: 	true,
            "renderer" 	: 	columnWrap,
            "align"		:	"left",
            "type"		: 	"string"
        }
    },{
    	column:{
            "dataIndex"	: 	"salesAmount",
            "name"		: 	"月销",
            "width"		: 	45,
            "sortable"	: 	true,
            "renderer" 	: 	defaultRenderer,
            "align"		:	"right",
            "type"		: 	"int"
        }
    },{
    	column:{
            "dataIndex"	:	 "unitPrice",
            "name"		: 	"原价",
            "width"		: 	55,
            "sortable"	: 	true,
            "renderer" 	: 	defaultRenderer,
            "align"		:	"right",
            "type"		: 	"float"
        }
    },{
    	column:{
            "dataIndex"	: 	"total",
            "name"		: 	"销售额",
            "width"		: 	75,
            "sortable"	: 	true,
            "renderer" 	: 	defaultRenderer,
            "align"		:	"right",
            "type"		: 	"float"
        }
    },{
    	column:{
            "dataIndex"	: 	"productId",
            "name"		: 	"ID",
            "width"		:	100,
            "sortable"	: 	true,
            "renderer" 	: 	linkChange,
            "align"		:	"center",
            "type"		: 	"string"
        }
    }];
    
    
    var jsonString = '{"success":"false","data":[{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"1","name":"史坦利官方旗舰店","products":[{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"2","summary":{"link":"http://detail.tmall.com/item.htm?id=15374250987&","monthlySalesAmount":"1662","price":"528","productId":"15374250987","rating":"4.8","salesTotal":"773939","salesTotalAmount":"1647","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T11MHVXd0XXXcp_0MW_022853.jpg_160x160.jpg","title":"【爆款包邮】史丹利41件五金工具套装 家用维修工具箱套装"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"1","summary":{"link":"http://detail.tmall.com/item.htm?id=18757476160&","monthlySalesAmount":"1266","price":"359","productId":"18757476160","rating":"4.84","salesTotal":"446294","salesTotalAmount":"1270","thumbnail":"http://img01.taobaocdn.com/bao/uploaded/i1/T1dqP0XlFgXXa9Aaja_090121.jpg_160x160.jpg","title":"【爆款2000件！】史丹利25件五金工具套装家用工具箱包邮维修手动"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"4","summary":{"link":"http://detail.tmall.com/item.htm?id=16002226102&","monthlySalesAmount":"234","price":"699","productId":"16002226102","rating":"4.85","salesTotal":"127286","salesTotalAmount":"179","thumbnail":"http://img04.taobaocdn.com/bao/uploaded/i4/T1Xvz_XnxbXXcJMl39_104447.jpg_160x160.jpg","title":"史丹利百得冲击钻套装550W电钻/冲击钻两用 正反转无级变速家用"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"16","summary":{"link":"http://detail.tmall.com/item.htm?id=17626527677&","monthlySalesAmount":"247","price":"398","productId":"17626527677","rating":"4.9","salesTotal":"83989","salesTotalAmount":"159","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T1wSYZXfVeXXcZg5wW_024052.jpg_160x160.jpg","title":"史丹利百得冲击钻 500W家用冲击钻/电钻两用套装 多功能"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"96","summary":{"link":"http://detail.tmall.com/item.htm?id=20304528712&","monthlySalesAmount":"44","price":"1259","productId":"20304528712","rating":"4.91","salesTotal":"48896","salesTotalAmount":"37","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T1xdf2XlFbXXaC4OA2_044449.jpg_160x160.jpg","title":"史丹利百得鸭嘴式高强旋转无线吸尘器 充电吸尘器 家用多功能"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"41","summary":{"link":"http://detail.tmall.com/item.htm?id=15792760349&","monthlySalesAmount":"64","price":"768","productId":"15792760349","rating":"4.88","salesTotal":"46062","salesTotalAmount":"74","thumbnail":"http://img01.taobaocdn.com/bao/uploaded/i1/T1Tif2XaBaXXcmjhEZ_032525.jpg_160x160.jpg","title":"史丹利百得冲击钻套装710W 大功率电钻 两用多功能无极调速正反转"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"7","summary":{"link":"http://detail.tmall.com/item.htm?id=20309348620&","monthlySalesAmount":"37","price":"1100","productId":"20309348620","rating":"4.67","salesTotal":"40700","salesTotalAmount":"41","thumbnail":"http://img02.taobaocdn.com/bao/uploaded/i2/T1dQf0XlpmXXaJshUT_012157.jpg_160x160.jpg","title":"【热销】包邮史丹利百得高压清洗机 家用高压水枪洗车机洗车器"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"8","summary":{"link":"http://detail.tmall.com/item.htm?id=13891315229&","monthlySalesAmount":"192","price":"199","productId":"13891315229","rating":"5.0","salesTotal":"39129","salesTotalAmount":"221","thumbnail":"http://img02.taobaocdn.com/bao/uploaded/i2/T1R8LPXhFoXXcYY0c5_055301.jpg_160x160.jpg","title":"【全身水洗】百得多功能厨房清洁宝电动刷 清洁刷 刷子洗碗刷"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"17","summary":{"link":"http://detail.tmall.com/item.htm?id=16713191279&","monthlySalesAmount":"106","price":"329","productId":"16713191279","rating":"4.89","salesTotal":"34811","salesTotalAmount":"137","thumbnail":"http://img02.taobaocdn.com/bao/uploaded/i2/T1bSL0XaXeXXbgXNI2_044340.jpg_160x160.jpg","title":"【包邮】史丹利百得电动螺丝刀 3.6伏旋转手柄电起子 正反转"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"97","summary":{"link":"http://detail.tmall.com/item.htm?id=19318172825&","monthlySalesAmount":"52","price":"725","productId":"19318172825","rating":"4.86","salesTotal":"33800","salesTotalAmount":"38","thumbnail":"http://img02.taobaocdn.com/bao/uploaded/i2/T1mGL2XhpfXXcLCoPb_125510.jpg_160x160.jpg","title":"【全自动】史丹利百得水平仪十字激光水平仪5线1点原理BDL310S"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"34","summary":{"link":"http://detail.tmall.com/item.htm?id=20605512526&","monthlySalesAmount":"52","price":"598","productId":"20605512526","rating":"4.83","salesTotal":"27013","salesTotalAmount":"46","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T1Ier0XileXXc.ej70_035757.jpg_160x160.jpg","title":"史丹百得12V充电钻 无线电钻 家用电钻 多功能正反转可调速"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"21","summary":{"link":"http://detail.tmall.com/item.htm?id=14764268059&","monthlySalesAmount":"52","price":"532","productId":"14764268059","rating":"4.73","salesTotal":"22228","salesTotalAmount":"239","thumbnail":"http://img02.taobaocdn.com/bao/uploaded/i2/T1vXDRXj4gXXa_VfA5_055602.jpg_160x160.jpg","title":"【包邮】史丹利 18件套高级通用工具套装维护维修家用 90-597-23"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"74","summary":{"link":"http://detail.tmall.com/item.htm?id=20626340297&","monthlySalesAmount":"161","price":"99","productId":"20626340297","rating":"4.37","salesTotal":"15839","salesTotalAmount":"170","thumbnail":"http://img02.taobaocdn.com/bao/uploaded/i2/T1lHf0XgpfXXc83Eg3_051424.jpg_160x160.jpg","title":"史丹利三脚架手电筒 LED节能手电 家用汽修必备"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"44","summary":{"link":"http://detail.tmall.com/item.htm?id=16269342524&","monthlySalesAmount":"38","price":"458","productId":"16269342524","rating":"4.0","salesTotal":"14701","salesTotalAmount":"24","thumbnail":"http://img04.taobaocdn.com/bao/uploaded/i4/T1lZD0XkteXXa0jpU__105239.jpg_160x160.jpg","title":"百得抛光机 家庭小型打蜡抛光机 车用打蜡机 汽车清洁抛光"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"12","summary":{"link":"http://detail.tmall.com/item.htm?id=13464390676&","monthlySalesAmount":"24","price":"660","productId":"13464390676","rating":"5.0","salesTotal":"14656","salesTotalAmount":"36","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T1Ui2LXb8rXXcaC5Z3_050440.jpg_160x160.jpg","title":"【无线吸尘第一品牌】百得气旋式无线吸尘器 充电式家用吸尘器"}}],"url":"http://stanley.tmall.com/search.htm"},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"2","name":"力成工具官方旗舰店","products":[{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"102","summary":{"link":"http://detail.tmall.com/item.htm?id=16667355208&","monthlySalesAmount":"2344","price":"320","productId":"16667355208","rating":"4.79","salesTotal":"571216","salesTotalAmount":"9174","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T1gg_NXe0hXXc1J8I3_051104.jpg_160x160.jpg","title":"力成工具 82件家用五金 工具组合套装 组套工具箱 包邮热销"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"101","summary":{"link":"http://detail.tmall.com/item.htm?id=14768033621&","monthlySalesAmount":"2257","price":"199","productId":"14768033621","rating":"4.79","salesTotal":"389243","salesTotalAmount":"13648","thumbnail":"http://img01.taobaocdn.com/bao/uploaded/i1/T1v1DdXXBpXXaBrGs3_045751.jpg_160x160.jpg","title":"力成工具 78件家用维修五金工具组合套装 组套工具箱 套装工具箱"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"109","summary":{"link":"http://detail.tmall.com/item.htm?id=9594062235&","monthlySalesAmount":"331","price":"399","productId":"9594062235","rating":"4.75","salesTotal":"120528","salesTotalAmount":"871","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T1bnzMXi8jXXaedx2X_085446.jpg_160x160.jpg","title":"力成工具 铬钒钢51件电工工具组合套装 家用五金维修工具箱 包邮"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"120","summary":{"link":"http://detail.tmall.com/item.htm?id=9593449597&","monthlySalesAmount":"307","price":"299","productId":"9593449597","rating":"4.71","salesTotal":"89486","salesTotalAmount":"1191","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T1nNfNXcdcXXaCQevX_084717.jpg_160x160.jpg","title":"力成工具 32件络钒钢工具箱组合套装 家用维修五金工具组套 包邮"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"103","summary":{"link":"http://detail.tmall.com/item.htm?id=8499825615&","monthlySalesAmount":"1039","price":"79","productId":"8499825615","rating":"4.81","salesTotal":"82081","salesTotalAmount":"21141","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T1R8q3XkhcXXXq3q72_042913.jpg_160x160.jpg","title":"力成工具 65件家用维修五金工具箱组合套装 家用工具箱 热销两万"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"104","summary":{"link":"http://detail.tmall.com/item.htm?id=20601044934&","monthlySalesAmount":"551","price":"68","productId":"20601044934","rating":"4.94","salesTotal":"36109","salesTotalAmount":"512","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T1Z.fTXfVnXXcbV2Db_124029.jpg_160x160.jpg","title":"力成工具 45合一 螺丝刀套装 螺丝批组合 电脑拆机工具 可拆苹果"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"105","summary":{"link":"http://detail.tmall.com/item.htm?id=14473118770&","monthlySalesAmount":"514","price":"44","productId":"14473118770","rating":"4.74","salesTotal":"24459","salesTotalAmount":"3043","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T1h7Y.Xg0XXXcSYgI8_070541.jpg_160x160.jpg","title":"力成工具 38合一笔记本维修/手机拆机工具 螺丝刀组合套装镙丝刀"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"135","summary":{"link":"http://detail.tmall.com/item.htm?id=17671091275&","monthlySalesAmount":"136","price":"158","productId":"17671091275","rating":"5.0","salesTotal":"20228","salesTotalAmount":"129","thumbnail":"http://img02.taobaocdn.com/bao/uploaded/i2/T13kH0XbtjXXaK57.._113224.jpg_160x160.jpg","title":"力成工具 66合一套筒螺丝刀组合工具套装 棘轮螺丝批 改锥"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"110","summary":{"link":"http://detail.tmall.com/item.htm?id=8848816881&","monthlySalesAmount":"316","price":"79","productId":"8848816881","rating":"4.82","salesTotal":"20171","salesTotalAmount":"1213","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T1o46PXb4gXXczy5.1_041603.jpg_160x160.jpg","title":"atomic/力成工具 12件家用五金 工具组合套装 组套工具箱 维修"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"115","summary":{"link":"http://detail.tmall.com/item.htm?id=16260294876&","monthlySalesAmount":"183","price":"67","productId":"16260294876","rating":"0.0","salesTotal":"12844","salesTotalAmount":"241","thumbnail":"http://img04.taobaocdn.com/bao/uploaded/i4/T18tn1XklfXXc_y5U8_100823.jpg_160x160.jpg","title":"力成工具 多功能13寸16寸塑料工具箱 家用五金工具箱 工具盒"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"144","summary":{"link":"http://detail.tmall.com/item.htm?id=9265519517&","monthlySalesAmount":"87","price":"59","productId":"9265519517","rating":"4.36","salesTotal":"6565","salesTotalAmount":"123","thumbnail":"http://img02.taobaocdn.com/bao/uploaded/i2/T1JBm5XktfXXaIVbM0_034422.jpg_160x160.jpg","title":"atomic/力成工具 家用五金螺丝刀套装组合 电脑维修修表工具套装"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"111","summary":{"link":"http://detail.tmall.com/item.htm?id=12473740843&","monthlySalesAmount":"28","price":"255","productId":"12473740843","rating":"4.85","salesTotal":"6200","salesTotalAmount":"229","thumbnail":"http://img02.taobaocdn.com/bao/uploaded/i2/T1u6jnXcNoXXcmw67U_015401.jpg_160x160.jpg","title":"atomic/力成工具 300KG折叠平板车 手推车 工具车"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"131","summary":{"link":"http://detail.tmall.com/item.htm?id=19851057000&","monthlySalesAmount":"68","price":"89","productId":"19851057000","rating":"5.0","salesTotal":"5580","salesTotalAmount":"74","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T1O7YLXidkXXcukOzb_094411.jpg_160x160.jpg","title":"力成工具 双面元件盒 零件盒 工具盒收纳盒 可活动 螺丝盒 工具箱"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"107","summary":{"link":"http://detail.tmall.com/item.htm?id=8861718713&","monthlySalesAmount":"100","price":"39","productId":"8861718713","rating":"4.84","salesTotal":"5540","salesTotalAmount":"659","thumbnail":"http://img03.taobaocdn.com/bao/uploaded/i3/T1qkDtXb4dXXaAbLQW_023419.jpg_160x160.jpg","title":"atomic/力成工具 强力大号马桶疏通器 通马桶疏通器一炮通 皮搋子"}},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"136","summary":{"link":"http://detail.tmall.com/item.htm?id=9593582203&","monthlySalesAmount":"28","price":"199","productId":"9593582203","rating":"4.69","salesTotal":"3552","salesTotalAmount":"440","thumbnail":"http://img01.taobaocdn.com/bao/uploaded/i1/T1ZcLJXgVcXXbbAp7Y_025326.jpg_160x160.jpg","title":"atomic/力成工具 络钒钢 多功能 15件家用五金工具包套装"}}],"url":"http://atomic.tmall.com/search.htm"},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"3","name":"开拓官方旗舰店","url":"http://exploit.tmall.com/search.htm"},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"4","name":"百锐官方旗舰店","url":"http://berent.tmall.com/search.htm"},{"createdBy":"sanwisdom","updatedBy":"sanwisdom","id":"5","name":"闪电家居专营店","url":"http://shandianjj.tmall.com/search.htm"}]}';
	 
    var shops = [{
        "name":"开拓旗舰店"
    },{
        "name":"史坦力旗舰店"
    },{
        "name":"力成旗舰店"
    }];
    
    function createDynamicFields(columnTemplate, numberOfShops) {
	   	 var fields = [
	   	               {name: 'top', type: 'int'}
	   	               ];
	   	for (var i = 0; i < numberOfShops; i++) {
	   		for (var j = 0; j < columnTemplate.length; j++) {
	   			fields[i*columnTemplate.length + j + 1] =  {name: columnTemplate[j].column.dataIndex + '' + i, type: columnTemplate[j].column.type};
	           }
	   	}
	   	return fields;
   };
    
    var dynamicFields = createDynamicFields(columnTemplate, shops.length);
    
    var store = createDynamicStore1(dynamicFields, myData);
    
    function createDynamicStore(dynamicFields, myData) {
    	return Ext.create('Ext.data.ArrayStore', {
            fields: dynamicFields,
            data: myData
        });
    };
  
    function createDynamicStore1(dynamicFields, myData) {
    	var jsonObject = Ext.decode(jsonString);
    	var numberOfShops = jsonObject.data.length;
    	var dynamicFieldsTemp = [
	   	               {name: 'top', type: 'int'}
	   	               ];
    	var max = 0;
    	for (var i = 0; i < numberOfShops; i++) {
    		for (var j = 0; j < columnTemplate.length; j++) {
	   			dynamicFieldsTemp[i*columnTemplate.length + j + 1] =  {name: columnTemplate[j].column.dataIndex + '' + i, type: columnTemplate[j].column.type};
	        }
	   		if (Ext.isDefined(jsonObject.data[i].products) && max < jsonObject.data[i].products.length) {
    			max = jsonObject.data[i].products.length;
    		}
	   	}
    	var length = 15;
    	var myDataTemp = [];
    	for (var j = 0; j < length; j++) {
    		var myRecord = [];  
    		var k = 0;
    		myRecord[k] = j + 1; 
    		for (var i = 0; i < numberOfShops; i++) {    			
        		if (Ext.isDefined(jsonObject.data[i].products) 
        				&& Ext.isDefined(jsonObject.data[i].products[j])
        				&& null != jsonObject.data[i].products[j]) {
        			var product = jsonObject.data[i].products[j];
        			myRecord[++k] = product.summary.thumbnail;
        			myRecord[++k] = product.summary.title;
        			myRecord[++k] = product.summary.monthlySalesAmount;
        			myRecord[++k] = product.summary.price;
        			myRecord[++k] = product.summary.salesTotal;
        			myRecord[++k] = product.summary.productId;
        		} else {
        			myRecord[++k] = null;
        			myRecord[++k] = null;
        			myRecord[++k] = null;
        			myRecord[++k] = null;
        			myRecord[++k] = null;
        			myRecord[++k] = null;
        		}
        	}
    		myDataTemp[j] = myRecord;
    	}	
    	return Ext.create('Ext.data.ArrayStore', {
            fields: dynamicFieldsTemp,
            data: myDataTemp,
        });
    };

    // create the Grid column
    function createDynamicGridColumns(columnTemplate, shops) {
    	
    	var dynColumns = [{
            text     : 'Top</br>销量',
         	width    : 40,
         	sortable : true,
            renderer : 	defaultRenderer,
         	dataIndex: 'top'
    	}];
    	for (var i = 0; i < shops.length; i++) {
    		var shopColumn = {
    			text: shops[i].name,
    			columns: []
    		};
    		for (var j = 0; j < columnTemplate.length; j++) {
    			shopColumn.columns[i*columnTemplate.length + j + 1] =  {
                        text     : columnTemplate[j].column.name,
                        width    : columnTemplate[j].column.width,
                        sortable : columnTemplate[j].column.sortable,
                        renderer : columnTemplate[j].column.renderer,
                        dataIndex: columnTemplate[j].column.dataIndex + '' + i
                	};
            }
    		dynColumns[i + 1] = shopColumn;
    	};
    	return dynColumns;
    };
    
    // create the Grid column
    function createDynamicGridColumns1(columnTemplate, result) {
    	var jsonObject = Ext.decode(result);
    	var dynColumns = [{
            text     : 'Top</br>销量',
         	width    : 40,
         	sortable : true,
         	renderer : 	defaultRenderer,
         	dataIndex: 'top'
    	}];
    	var shops = jsonObject.data;
    	for (var i = 0; i < shops.length; i++) {
    		var shopColumn = {
    			text: Ext.isDefined(shops[i].products) ?　shops[i].name : shops[i].name + "(无本月监测数据)",
    			columns: []
    		};
    		for (var j = 0; j < columnTemplate.length; j++) {
    			shopColumn.columns[i*columnTemplate.length + j + 1] =  {
                        text     : columnTemplate[j].column.name,
                        width    : columnTemplate[j].column.width,
                        sortable : columnTemplate[j].column.sortable,
                        renderer : columnTemplate[j].column.renderer,
                        style	 : 'vertical-align: middle; text-align: center',
                        align	 : columnTemplate[j].column.align,
                        dataIndex: columnTemplate[j].column.dataIndex + '' + i
                	};
            }
    		dynColumns[i + 1] = shopColumn;
    	};
    	return dynColumns;
    };
    
    
    
    var dynamicColumns = createDynamicGridColumns1(columnTemplate, jsonString);
    
    function createDynamicGrid() {
    	var grid = Ext.create('Ext.grid.Panel', {
        	id: 'magic-monitor-grid-id',
            store: store,
            columnLines: true,
            columns: dynamicColumns,
            height: 1000,
//            autoHeight: true,
            width: 2000,
            title: '五金工具类监测',
            renderTo: 'grid-example',
            style: 'vertical-align: middle; text-align: center',
            viewConfig: {
                stripeRows: true
            }
        });
    	grid.getStore().on('load', function anonym(){
        	grid.setHeight(this.getCount() * 16); // to be calculated
        });
    	return grid;
    };
    
    var grid = createDynamicGrid();
    
    var grid1 = Ext.create('Ext.grid.Panel', {
    	id: 'magic-monitor-grid-id1',
        store: store1,
        columnLines: true,
        columns: [{
            text     : 'Top</br>销量',
            width    : 40,
            sortable : true,
            dataIndex: 'top'
        }, {
            text: '开拓旗舰店',
            columns: [{
                text     : '产品图',
                width    : 90,
                sortable : true,
                renderer : thumbChange,
                dataIndex: 'thumbnail'
            }, {
                text     : '品名',
                width    : 175,
                sortable : true,
                renderer : columnWrap,
                dataIndex: 'productName'
            }, {
                text     : '月销',
                width    : 45,
                sortable : true,
                dataIndex: 'salesAmount'
            }, {
                text     : '原价',
                width    : 55,
                sortable : true,
                align: 'right',
                dataIndex: 'unitPrice'
            }, {
                text     : '销售总额',
                width    : 75,
                sortable : true,
                align: 'right',
                dataIndex: 'total'
            }, {
                text     : 'ID',
                width    : 100,
                sortable : true,
                renderer : linkChange,
                dataIndex: 'productId'
            }]
        }],
        height: 600,
        width: 1000,
        title: '五金工具类监测',
        renderTo: 'grid-example1',
        viewConfig: {
            stripeRows: true
        }
    });
});
