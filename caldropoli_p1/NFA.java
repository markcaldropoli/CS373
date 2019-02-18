import java.util.*;

public class NFA {
    private ArrayList<ArrayList<String>> config = new ArrayList<ArrayList<String>>();
    private ArrayList<Integer> acceptStates = new ArrayList<Integer>();
    private boolean reject = false;
    private boolean[] isAcceptState = new boolean[1001];
    private HashSet<String> accepted = new HashSet<String>();
    private HashSet<String> rejected = new HashSet<String>();
    private Map<String, ArrayList<String>> transitions = new HashMap<String, ArrayList<String>>();
    private String start = "";

    public void setStart(String startState, String input) {
        start = startState;
        config.add(new ArrayList<String>(Arrays.asList(startState,input)));
    }

    public void setAccept(String state) {
        int s = Integer.parseInt(state);
        isAcceptState[s] = true;
        acceptStates.add(s);
    }

    public void setTransition(String trans, String result) {
        if(!transitions.containsKey(trans)) transitions.put(trans, new ArrayList<String>());
        transitions.get(trans).add(result);
    }

    public void createNFA() {
        while(config.size() != 0) {
            String s = config.get(0).get(1);

            if(!s.isEmpty()) {
                String e = s.substring(1);
                String k = config.get(0).get(0) + config.get(0).get(1).charAt(0);

                if(transitions.containsKey(k)) {
                    ArrayList<String> states = new ArrayList<>(transitions.get(k));

                    for(int i = 0; i < states.size(); i++) {
                        String ns = states.get(i);
                        int x = Integer.parseInt(ns);

                        if(e.isEmpty()) {
                            if(isAcceptState[x]) {
                                if(!accepted.contains(ns)) accepted.add(ns);
                            }
                            
                            if(!isAcceptState[x] && accepted.size() == 0) {
                                if(!rejected.contains(ns)) rejected.add(ns);
                            }
                        } else {
                            ArrayList<String> conf = new ArrayList<String>(Arrays.asList(states.get(i), e));
                            config.add(conf);
                        }
                    }
                } else reject = true;
            } else {
                String cstate = config.get(0).get(0);
                int x = Integer.parseInt(cstate);

                if(isAcceptState[x]) accepted.add(cstate);
                else rejected.add(cstate);
            }
            config.remove(0);
        }
    }

    public void print() {
        if(accepted.size() > 0) {
            System.out.print("accept ");
            for(String s : accepted) System.out.print(s + " ");
        } else {
            System.out.print("reject ");
            for(String s : rejected) System.out.print(s + " ");
        }

        System.out.println();
    }
}
