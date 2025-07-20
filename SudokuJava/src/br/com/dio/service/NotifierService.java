package br.com.dio.service;

import static br.com.dio.service.EventEnum.CLEAR_SPACE;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;



public class NotifierService {

    private final Map<EventEnum, List<EventListener>> listeners = new HashMap<>(){{
        put(CLEAR_SPACE, new ArrayList<>());
    }};

    public void subscriber(final EventEnum eventType, EventListener listener){
        var selectedListeners = listeners.get(eventType);
        selectedListeners.add(listener);
    }

    public void notify(final EventEnum eventType){
        listeners.get(eventType).forEach(l -> l.update(eventType));
    }

}
