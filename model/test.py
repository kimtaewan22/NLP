import numpy as np
import pandas as pd
import os


# with open("train.tsv", encoding="utf-8") as f:
#     lines = f.readlines()
    
# for line in lines[:3]:
#     print(line)


data = pd.read_csv("train.tsv", delimiter='\t', encoding='utf-8')
print(data.head())
