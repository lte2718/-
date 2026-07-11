首次打开默认打开图形界面，按提示输入两个种子，长度，实际长度大于等于输入长度，当类型为字符时实际长度大约只能达到10，当类型为数字时，大约20，因为long长度的限制。种子可以是任意字符需设置。你可以通过加参数的方法快速填充和设置，如：
G{4,3,9,0}	或	G{4,3,9,字符}	前两项是种子，第三项是长度，第四项是输出类型（可以是设置中的序号，也可以是全名，选填），开头用"G"表生成密码，随后用"{}"包裹，项中间用","(英文)
S{2,1}	或	S{界面,CLI}		与使用设置方法一致只是输入方式不同（可以是设置中的序号，也可以是全名），开头用"S"表设置，随后用"{}"包裹，项中间用","(英文)
暂时没有除中文外的其他语言。GUI暂时没有加密功能。安装程序无法更改安装目录否则请用jar。欢迎帮助开发。
S{0,1}	打开调试模式	S{0,0}关闭
配置文件"C:\Users\%USERNAME%\AppData\Local\r1data"暂无法更改
Upon first launch, the graphical interface opens by default. Enter two seeds and a length, where the actual length must be greater than or equal to the input length. When the type is "character," the actual length can only reach approximately 10, while for "numeric" types, it can reach around 20 due to the limit of the long data type. Seeds can be any characters and need to be set. You can quickly fill and set values by adding parameters, such as:
G{4,3,9,0}	or	G{4,3,9,字符}	The first two items are seeds, the third item is length, and the fourth item is output type (which can be the serial number in the settings or the full name, optional). Use the "G" table to generate a password at the beginning, then wrap it with "{}", and use "," (in English) in the middle of the item.
S{2,1}	or	S{界面,CLI}		Same as the setting method, only the input method is different (it can be the serial number in the setting or the full name), starting with the "S" table setting, then wrapping with "{}", and using "," in the middle of the item (in English).
There are currently no other languages besides Chinese. The GUI currently does not have encryption function. The installation program cannot change the installation directory, otherwise please use jar.Welcome to help with development.
S{0,1}	Open debugging mode	S{0,0}	close
configuration file"C:\Users\%USERNAME%\AppData\Local\r1data"Cannot be changed temporarily.