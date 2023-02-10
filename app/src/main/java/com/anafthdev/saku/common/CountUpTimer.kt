package com.anafthdev.saku.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CountUpTimer @Inject constructor() {
	
	private var timerJob: Job? = null
	private var listener: CountUpTimerListener? = null
	
	private val _second = MutableStateFlow(0)
	val second: StateFlow<Int> = _second
	
	fun start() {
		timerJob = CoroutineScope(Dispatchers.IO).launch {
			while (true) {
				delay(1000)
				
				_second.emit(second.value + 1)
				
				listener?.onTick()
			}
		}
	}
	
	fun cancel() {
		timerJob?.cancel()
		timerJob = null
	}
	
	fun setListener(l: CountUpTimerListener) {
		listener = l
	}
	
	interface CountUpTimerListener {
		
		fun onTick()
	}
	
}