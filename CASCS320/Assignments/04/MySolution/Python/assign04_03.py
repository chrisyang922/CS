####################################################
#!/usr/bin/env python3
####################################################
"""
HX-2023-06-19: 10 points
You are required to implement the following function
generator_merge2 WITHOUT using streams. A solution that
uses streams is disqualified.
"""
def generator_merge2(gen1, gen2, lte3):
    """
    Given two generators gen1 and gen2 and a comparison
    function lte3, the function generator_merge2 returns
    another generator that merges the elements produced by
    gen1 and gen2 according to the order specified by lte3.
    The function generator_merge2 is expected to work correctly
    for both finite and infinite generators.
    """
    x1 = next(gen1)
    x2 = next(gen2)
    while True:
        if lte3(x1, x2):
            yield x1
            try:
                x1 = next(gen1)
            except StopIteration:
                yield x2 
                while True:
                    try: 
                        x2 = next(gen2)
                        yield x2
                    except StopIteration:
                        return
        else:
            yield x2 
            try:
                x2 = next(gen2)
            except StopIteration:
                yield x1 
                while True:
                    try: 
                        x1 = next(gen1)
                        yield x1
                    except StopIteration:
                        return

    
    
####################################################
