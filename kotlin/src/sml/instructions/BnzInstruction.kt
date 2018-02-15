package sml.instructions

import sml.Instruction
import sml.Machine

class BnzInstruction(label: String, val register: Int, val jumpTo: String) : Instruction(label, "bnz") {

    override fun execute(m: Machine) {
        if (m.registers.getRegister(register) != 0){
            val position: Int = m.labels.getLabels().indexOf(jumpTo)
            m.pc = position
        }
    }

    override fun toString(): String {
        return super.toString() + " " + register + " Jump to: " + jumpTo
    }
}
