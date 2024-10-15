
# Создание директории
mkdir C:pathtodirectory

# Переход в созданную директорию
cd C:pathtodirectory

# Создание файла
echo Это тестовый файл > testfile.txt

# Из другого окна Командной строки
# Папка, из которой нужно удалить все файлы
$TARGET_DIR = "C:\Users\Пользователь\pathtodirectory"

# Проверка, существует ли директория
if (-Not (Test-Path $TARGET_DIR)) {
    Write-Host "Указанная директория не существует: $TARGET_DIR"
    exit 1
}

# Удаление всех файлов в директории
Remove-Item "$TARGET_DIR*" -Force

# Удаление всех файлов в поддиректориях
Get-ChildItem -Path $TARGET_DIR -Recurse -File | Remove-Item -Force

Write-Host "Все файлы в директории $TARGET_DIR были удалены."

