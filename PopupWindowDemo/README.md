# PopupWindow的简单使用
![popupwindowf](http://upload-images.jianshu.io/upload_images/947286-2e50eef7b7ab6313.gif?imageMogr2/auto-orient/strip)

1. 创建布局视图
```java
View popView = getLayoutInflater().inflate(R.layout.yourLayoutId, null,false);
// todo 设置内容
```

2. 创建PopupWindow
```java
PopupWindow mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
// 点击其他地方消失
mPopupWindow.setOutsideTouchable(true);
```

3. 显示
```java
//显示在基准控件下方,用于代表下拉列表
mPopupWindow.showAsDropDown(tvTitle);
 ```
 
 P.S. 需要设置popupWindow的背景图片,不然在6.0以下机子中`setOutsideTouchable()`可能失效,原因请参考 [这篇文章](http://www.jianshu.com/p/7b42fc303bf8) ;