![Android](https://javagitcoder.github.io/assets/gitbook/images/japangoods-android.png "Android")
```python

import sqlite3
import os
import sys

def create_snacks_database():
    """创建日本零食数据库"""
    
    # 定义数据库文件路径
    db_file = 'snacks.db'
    
    # 如果数据库已存在，先删除
    if os.path.exists(db_file):
        try:
            os.remove(db_file)
            print(f"已删除已存在的数据库文件: {db_file}")
        except Exception as e:
            print(f"删除旧数据库文件失败: {e}")
            return False
    
    try:
        # 创建数据库连接
        conn = sqlite3.connect(db_file)
        cursor = conn.cursor()
        print("✓ 数据库连接创建成功")
        
        # 创建零食表
        cursor.execute('''
        CREATE TABLE snacks (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            title TEXT NOT NULL,
            japanese TEXT NOT NULL,
            english TEXT NOT NULL,
            description TEXT,
            image_name TEXT NOT NULL
        )
        ''')
        print("✓ 数据表创建成功")
        
        # 插入示例数据
        snacks_data = [
            # 巧克力类
            ("巧克力", "チョコレート", "Chocolate", "美味的巧克力零食", "chocolate1001.jpg"),
            ("抹茶巧克力", "抹茶チョコレート", "Matcha Chocolate", "日本抹茶风味巧克力", "chocolate1001.jpg"),
            ("草莓巧克力", "いちごチョコレート", "Strawberry Chocolate", "草莓夹心巧克力", "chocolate1001.jpg"),
            
            # 糖果类
            ("水果糖", "フルーツキャンディ", "Fruit Candy", "各种水果口味糖果", "sugar1001.jpg"),
            ("牛奶糖", "ミルクキャンディ", "Milk Candy", "香甜牛奶糖", "sugar1001.jpg"),
            ("汽水糖", "ラムネ", "Ramune Candy", "日本传统汽水糖", "sugar1001.jpg"),
            
            # 薯片类
            ("薯片", "ポテトチップス", "Potato Chips", "薄脆薯片", "potato-chips1001.jpg"),
            ("海苔薯片", "のりしおポテトチップス", "Seaweed Salt Potato Chips", "海苔盐味薯片", "potato-chips1001.jpg"),
            ("酱油薯片", "醤油ポテトチップス", "Soy Sauce Potato Chips", "日式酱油味薯片", "potato-chips1001.jpg"),
            
            # 杯面类
            ("杯面", "カップヌードル", "Cup Noodles", "方便杯面", "cup-noodles1001.jpg"),
            ("酱油杯面", "醤油カップ麺", "Soy Sauce Cup Noodles", "酱油风味杯面", "cup-noodles1001.jpg"),
            ("味增杯面", "味噌カップ麺", "Miso Cup Noodles", "味增风味杯面", "cup-noodles1001.jpg"),
            
            # 水果类
            ("草莓", "いちご", "Strawberry", "新鲜草莓", "pudding1001.jpg"),
            ("哈密瓜", "メロン", "Melon", "日本哈密瓜", "pudding1001.jpg"),
            ("葡萄", "ぶどう", "Grapes", "日本葡萄", "pudding1001.jpg"),
            
            # 其他零食
            ("仙贝", "せんべい", "Senbei", "日本米果", "rice-soup1001.jpg"),
            ("羊羹", "ようかん", "Yokan", "传统日式甜点", "yogurt1001.jpg"),
            ("大福", "だいふく", "Daifuku", "麻糬点心", "cookie1001.jpg"),
            ("铜锣烧", "どらやき", "Dorayaki", "红豆馅点心", "bath-bomb1001.jpg"),
            ("章鱼烧", "たこやき", "Takoyaki", "章鱼小丸子", "drink1001.jpg")
        ]
        
        # 插入数据
        cursor.executemany('''
        INSERT INTO snacks (title, japanese, english, description, image_name)
        VALUES (?, ?, ?, ?, ?)
        ''', snacks_data)
        
        # 提交更改
        conn.commit()
        print(f"✓ 成功插入 {len(snacks_data)} 条零食数据")
        
        # 验证数据
        cursor.execute("SELECT COUNT(*) FROM snacks")
        count = cursor.fetchone()[0]
        print(f"✓ 数据库验证: 共 {count} 条记录")
        
        # 显示示例数据
        print("\n=== 数据库内容示例 ===")
        cursor.execute("SELECT * FROM snacks LIMIT 3")
        sample_rows = cursor.fetchall()
        
        for row in sample_rows:
            print(f"ID: {row[0]}, 名称: {row[1]}, 日语: {row[2]}, 英语: {row[3]}")
        
        # 关闭连接
        conn.close()
        print("✓ 数据库连接已关闭")
        
        # 检查文件是否生成
        if os.path.exists(db_file):
            file_size = os.path.getsize(db_file)
            print(f"\n🎉 数据库文件生成成功！")
            print(f"📁 文件位置: {os.path.abspath(db_file)}")
            print(f"📊 文件大小: {file_size} 字节")
            return True
        else:
            print("❌ 数据库文件生成失败")
            return False
            
    except Exception as e:
        print(f"❌ 数据库创建失败: {e}")
        return False

def main():
    """主函数"""
    print("=" * 50)
    print("日本零食学习应用 - 数据库生成工具")
    print("=" * 50)
    
    success = create_snacks_database()
    
    if success:
        print("\n✅ 数据库生成完成！")
        print("\n下一步操作：")
        print("1. 将生成的 'snacks.db' 文件复制到 Android 项目的")
        print("   app/src/main/assets/ 目录中")
        print("2. 如果 assets 目录不存在，请先创建")
        print("3. 在 Android Studio 中重新构建项目")
    else:
        print("\n❌ 数据库生成失败，请检查错误信息")
        sys.exit(1)

if __name__ == "__main__":
    main()
```
