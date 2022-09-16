((p <= q) and (q <= r)) <= (p <= r)                 
((not p or q) and (q <= r)) <= (p <= r)                                 # Conditional Identities
((not p or q) and (not q or r)) <= (p <= r)                             # Conditional Identities
((not p or q) and (not q or r)) <= (not p or r)                         # Conditional Identities
not((not p or q) and (not q or r)) or (not p or r)                      # Conditional Identities
not(not p or q) or not(not q or r) or (not p or r)                      # De Morgan's Laws
(not not p and not q) or not(not q or r) or (not p or r)                # De Morgan's Laws
(p and not q) or not(not q or r) or (not p or r)                        # Double Negation Laws
(p and not q) or (not not q and not r) or (not p or r)                  # De Morgan's Laws
(p and not q) or (q and not r) or (not p or r)                          # Double Negation Laws
(p and not q) or (not p or r) or (q and not r)                          # Commutative Laws
(not p or r) or (p and not q) or (q and not r)                          # Commutative Laws
(r or not p) or (p and not q) or (q and not r)                          # Commutative Laws
r or (not p or (p and not q)) or (q and not r)                          # Associative Laws
(not p or (p and not q)) or r or (q and not r)                          # Commutative Laws
(not p or (p and not q)) or (r or (q and not r))                        # Associative Laws
((not p or p) and (not p or not q)) or (r or (q and not r))             # Distributive Laws
((not p or p) and (not p or not q)) or ((r or q) and (r or not r))      # Distributive Laws
((p or not p) and (not p or not q)) or ((r or q) and (r or not r))      # Commutative Laws
((True) and (not p or not q)) or ((r or q) and (r or not r))            # Complement Laws
((True) and (not p or not q)) or ((r or q) and (True))                  # Complement Laws 
((not p or not q) and (True)) or ((r or q) and (True))                  # Commutative Laws
(not p or not q) or ((r or q) and (True))                               # Identity Laws
(not p or not q) or (r or q)                                            # Identity Laws
not p or (not q or r) or q                                              # Associative Laws
not p or (r or not q) or q                                              # Commutative Laws
not p or r or (not q or q)                                              # Associative Laws
not p or r or (q or not q)                                              # Commutative Laws
not p or r or True                                                      # Complement Laws
not p or (r or True)                                                    # Associative Laws
not p or True                                                           # Domination Laws
True                                                                    # Domination Laws




