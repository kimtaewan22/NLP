import re
import nltk
from nltk.tokenize import word_tokenize
import pandas as pd

nltk.download('punkt')

data = pd.read_csv("train.tsv", delimiter='\t', encoding='utf-8')

# 문장을 토큰화하고 특수 문자, 불필요한 공백 등을 제거
def preprocess_text(text):
    text = text.lower()
    text = re.sub(r'[^a-zA-Z가-힣0-9\s]', '', text)
    return text

data['comments'] = data['comments'].apply(preprocess_text)
data['comments_tokens'] = data['comments'].apply(word_tokenize)

print(data['comments_tokens'])