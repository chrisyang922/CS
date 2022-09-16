def is_reflexive(A, R):
    for x in A:
        if R(x,x) == False:
            return False
    return True

def is_injective(domain,f):
    for x in domain:
        for y in domain:
            if y != x:
                if f(x) == f(y):
                    return False
    return True

def is_transitive(A,R):
    for x in A:
        for y in A:
            for z in A:
                if not(not(R(x,y) and R(y,z)) or R(x,z)): 
                    return False
    return True

def are_parts_nonoverlapping(p):
    count = -1
    countTwo = -1 
    for x in p:
        count += 1
        countTwo = -1
        for y in p:
            countTwo += 1
            for a in x:
                for b in y:
                    if x!=y or countTwo != count:
                        if a == b:
                            return False
    return True

def do_parts_contain_element(x,p):
    for y in p:
        if x in y:
            return True
    return False

def do_parts_cover_set(s,p):
    for x in s:
        if not (do_parts_contain_element(x,p)):
            return False
    return True

def do_parts_have_nothing_extra(s,p):
    for x in p:
        for y in x:
            if y not in s:
                return False
    return True

def is_partition(s,p):
    for x in s:
        if do_parts_contain_element(x,p) == False:
            return False
    return are_parts_nonoverlapping(p) and do_parts_cover_set(s,p) and do_parts_have_nothing_extra(s,p)
