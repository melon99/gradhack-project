# -*- coding: utf-8 -*-
"""
generate the data for model trainning

"""

import random
import numpy as np
import pandas as pd

if __name__ == "__main__":
    
    random.seed(0)

    n = 1000
    data = []

    mu = 0
    sigma = 1
    error = np.random.normal(mu, sigma, n)

    for i in range(n):
        temp = []
        transaction = random.randint(0, 100)
        income = random.randint(0, 20000)
        overdue = random.randint(0, 20) # overdue payment
        level = random.randint(0, 10) # credict rating from 0 to 10
        
        # credict = 10 * transaction + 1.2 * income - 1000 * overdue + 1000 * level + 1000
        credit = 10 * transaction + 1.2 * income - 1000 * overdue + 1000 * level + 1000 + error[i]
        
        if credit < 0:
            credit = 0
        temp.append(transaction)
        temp.append(income)
        temp.append(overdue)
        temp.append(level)
        temp.append(credit)
        data.append(temp)
    
    df = pd.DataFrame(data, columns=["transaction", "income", "overdue", "level", "credit"])

    df.to_csv("./data.csv", index=False)
    

    